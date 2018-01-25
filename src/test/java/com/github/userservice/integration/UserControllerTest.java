package com.github.userservice.integration;

import com.github.userservice.data.models.User;
import com.github.userservice.helpers.StubBuilder;
import com.github.userservice.views.UserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:setupDatabase.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:tearDownDatabase.sql")
})
public class UserControllerTest {

    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @Before
    public void setUp() {
        //Using Apache HttpClient as HttpUrlConnection does not support patch
        restTemplate = new TestRestTemplate(new RestTemplate(new HttpComponentsClientHttpRequestFactory()));
        baseUrl = "http://localhost:" + port + "/v1/user";
    }

    @Test
    public void getUser_ShouldReturnAUser() {
        // GIVEN
        Long userId = 1010101L;

        ResponseEntity<User> actual = restTemplate.getForEntity(baseUrl + "/" + userId, User.class);

        // THEN
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).extracting("id", "email").containsOnly(userId, "test@email.co.uk");
    }

    @Test
    public void getUser_ShouldReturn404_WhenUserDoesNotExist() {
        // GIVEN
        Long userId = 110011L;

        ResponseEntity<Object> actual = restTemplate.getForEntity(baseUrl + "/" + userId, Object.class);

        // THEN
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).extracting("error", "message")
                .containsOnly("Not Found", "Could not find user by id: " + userId);
    }


    @Test
    public void postUser_ShouldCreateAndReturnSavedUser() {

        // GIVEN
        UserRequest request = UserRequest.builder().forename("Ada").surname("Lovelace").email("ada@test.co.uk").build();

        // WHEN
        ResponseEntity<User> actual = restTemplate.postForEntity(baseUrl, request, User.class);

        //THEN
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(actual.getHeaders().get("location").get(0)).isEqualTo("/v1/user/" + actual.getBody().getId());
        assertThat(actual.getBody().getEmail()).isEqualTo("ada@test.co.uk");
    }

    @Test
    public void postUser_ShouldReturn409_WhenUserWithEmailAlreadyExists() {

        // GIVEN
        UserRequest request = StubBuilder.userRequest();

        // WHEN
        ResponseEntity<Object> actual = restTemplate.postForEntity(baseUrl, request, Object.class);

        //THEN
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(actual.getBody()).extracting("error", "message")
                .containsOnly("Conflict", "User with email address " + request.getEmail() + " already exists");
    }

    @Test
    public void patchUser_shouldPartiallyUpdateUser() {
        // GIVEN
        Long userId = 1010101L;
        String payload = "{\"email\": \"newEmail@test.co.uk\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        HttpEntity<Object> entity = new HttpEntity<>(payload, headers);

        // WHEN
        ResponseEntity<Void> response = restTemplate.exchange(baseUrl + "/" + userId, HttpMethod.PATCH, entity, Void.class);

        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}