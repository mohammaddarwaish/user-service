package com.github.userservice.data.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.userservice.data.models.Role;
import com.github.userservice.helpers.StubBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findByRoleCode() {
        // GIVEN
        String roleCode = "ADMIN";
        entityManager.persistFlushFind(StubBuilder.role());

        // WHEN
        Optional<Role> actual = roleRepository.findByCode(roleCode);

        // THEN
        assertThat(actual).isNotEmpty()
                .flatMap(r -> Optional.of(r.getCode()))
                .hasValue("ADMIN");
    }

}
