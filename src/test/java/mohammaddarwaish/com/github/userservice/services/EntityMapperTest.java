package mohammaddarwaish.com.github.userservice.services;

import mohammaddarwaish.com.github.userservice.helpers.StubBuilder;
import mohammaddarwaish.com.github.userservice.helpers.TestEntityA;
import mohammaddarwaish.com.github.userservice.helpers.TestEntityB;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class EntityMapperTest {

    private EntityMapper entityMapper;

    @Before
    public void setUp() {
        entityMapper = new EntityMapper();
    }

    @Test
    public void mergeEntities_ShouldMergeTwoEntitiesByOverWritingEntityOneFieldsWithEntityTwo() {
        // GIVEN
        Long entityBId = StubBuilder.randomId();
        TestEntityB entityBOne = TestEntityB.builder().id(entityBId).fieldOne("entity B One field One").fieldTwo("entity B One field Two").build();

        TestEntityB entityBTwo = TestEntityB.builder().id(entityBId).fieldOne("entity B Two field One").fieldTwo("entity B Two field Two").build();

        // WHEN
        TestEntityB actual = entityMapper.mergeEntities(TestEntityB.class, entityBOne, entityBTwo);

        // THEN
        assertThat(actual.getId()).isEqualTo(entityBId);
        assertThat(actual.getFieldOne()).isEqualTo("entity B Two field One");
        assertThat(actual.getFieldTwo()).isEqualTo("entity B Two field Two");
    }

    @Test
    public void mergeFieldsWithEntity_ShouldMergeAllUpdatedFieldsExceptIds() {

        // GIVEN
        String fieldValue = "Value";
        String updatedValue = "New Value";

        Long entityBId = StubBuilder.randomId();
        TestEntityB entityB = TestEntityB.builder().id(entityBId).fieldOne(fieldValue).fieldTwo(fieldValue).build();

        Long entityAId = StubBuilder.randomId();
        TestEntityA entityA = TestEntityA.builder().id(entityAId).fieldOne(fieldValue).fieldTwo(fieldValue).testEntityB(entityB).build();

        Map<String, Object> entityBMap = new HashMap<>();
        entityBMap.put("id", entityBId);
        entityBMap.put("fieldTwo", updatedValue);

        Map<String, Object> entityAMap = new HashMap<>();
        entityAMap.put("id", entityAId);
        entityAMap.put("fieldOne", updatedValue);
        entityAMap.put("testEntityB", entityBMap);

        // WHEN
        TestEntityA actual = entityMapper.mergeFieldsWithEntity(TestEntityA.class, entityA, entityAMap);

        // THEN
        assertThat(actual.getId()).isEqualTo(entityAId);
        assertThat(actual.getFieldOne()).isEqualTo(updatedValue);
        assertThat(actual.getFieldTwo()).isEqualTo(fieldValue);
        assertThat(actual.getTestEntityB().getId()).isEqualTo(entityBId);
        assertThat(actual.getTestEntityB().getFieldOne()).isEqualTo(fieldValue);
        assertThat(actual.getTestEntityB().getFieldTwo()).isEqualTo(updatedValue);
    }

}