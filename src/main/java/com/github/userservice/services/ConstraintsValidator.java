package com.github.userservice.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConstraintsValidator {

    private final Validator validator;

    public <T> void validate(Class<T> beanType, Map<String, Object> fields) {
        Set<ConstraintViolation<T>> violations = fields.entrySet()
                .stream()
                .map(entry -> validator.validateValue(beanType, entry.getKey(), entry.getValue()))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        throwExceptionIfConstraintsViolated(violations);
    }

    public <T> void validate(T bean) {
        Set<ConstraintViolation<T>> violations = validator.validate(bean);
        throwExceptionIfConstraintsViolated(violations);
    }

    private <T> void throwExceptionIfConstraintsViolated(Set<ConstraintViolation<T>> violations) {
        if (!CollectionUtils.isEmpty(violations)) {
            throw new ConstraintViolationException(violations);
        }
    }

}
