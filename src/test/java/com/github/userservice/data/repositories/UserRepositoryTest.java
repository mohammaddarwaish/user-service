package com.github.userservice.data.repositories;

import com.github.userservice.data.models.User;
import com.github.userservice.helpers.StubBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findByEmail() {
        // GIVEN
        String email = "test@email.co.uk";
        entityManager.persistFlushFind(StubBuilder.user());

        // WHEN
        Optional<User> actual = userRepository.findByEmail(email);

        // THEN
        assertThat(actual).isNotEmpty()
                .flatMap(u -> Optional.of(u.getEmail()))
                .hasValue("test@email.co.uk");
    }
}