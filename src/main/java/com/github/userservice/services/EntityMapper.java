package com.github.userservice.services;

import static java.util.Collections.emptyList;
import static java.util.stream.Stream.concat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EntityMapper {

    private final ObjectMapper objectMapper;

    public EntityMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModules(new JavaTimeModule());
    }

    /**
     * Recursively deep merge an entity with a map of new values, where key = field name.
     */
    public <T> T mergeFieldsWithEntity(Class<T> clazz, T entity, Map<String, Object> updatedFields) {

        recursivelyRemoveIdFields(updatedFields);

        Map<String, Object> entityAsMap = objectMapper.convertValue(entity, new TypeReference<Map<String, Object>>() {
        });
        Map<String, Object> mergedEntityAsMap = deepMerge(entityAsMap, updatedFields);

        return objectMapper.convertValue(mergedEntityAsMap, clazz);
    }

    public <T> T mergeEntities(Class<T> clazz, T entity, T newEntity) {

        Map<String, Object> newEntityAsMap = objectMapper.convertValue(newEntity, new TypeReference<Map<String, Object>>() {
        });

        return mergeFieldsWithEntity(clazz, entity, newEntityAsMap);
    }

    @SuppressWarnings("unchecked")
    private void recursivelyRemoveIdFields(Map<String, Object> map) {

        map.remove("id");

        map.forEach((k, v) -> {
            if (v instanceof Map) {
                recursivelyRemoveIdFields((Map) v);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> deepMerge(Map<String, Object> originalMap, Map<String, Object> newMap) {
        return concat(originalMap.entrySet().stream(), newMap.entrySet().stream())
                .map(entry -> {
                    String key = entry.getKey();
                    if (isKeyInstanceOfMap(originalMap, newMap, key)) {
                        return new AbstractMap.SimpleEntry<>(key, deepMerge((Map<String, Object>) originalMap.get(key), (Map<String, Object>) newMap.get(key)));
                    }
                    if (isKeyInstanceOfListAndNewValueNull(originalMap, newMap, key)) {
                        return new AbstractMap.SimpleEntry<>(key, emptyList()); // Replace null collection values will empty lists
                    }
                    return entry;
                })
                .collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), HashMap::putAll);
    }

    private boolean isKeyInstanceOfListAndNewValueNull(Map<String, Object> originalMap, Map<String, Object> newMap, String key) {
        return originalMap.get(key) instanceof List && newMap.containsKey(key) && newMap.get(key) == null;
    }

    private boolean isKeyInstanceOfMap(Map<String, Object> originalMap, Map<String, Object> newMap, String key) {
        return newMap.get(key) instanceof Map && originalMap.get(key) instanceof Map;
    }
}
