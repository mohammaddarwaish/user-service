package com.github.userservice.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.userservice.data.models.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:setupDatabase.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:tearDownDatabase.sql")
})
public class RoleControllerTest {

    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @Before
    public void setUp() {
        //Using Apache HttpClient as HttpUrlConnection does not support patch
        restTemplate = new TestRestTemplate(new RestTemplate(new HttpComponentsClientHttpRequestFactory()));
        baseUrl = "http://localhost:" + port + "/v1/role";
    }

    @Test
    public void getRole_shouldReturnARole() {
        // GIVEN
        String roleCode = "ADMIN";

        // WHEN
        ResponseEntity<Role> actual = restTemplate.getForEntity(baseUrl + "/" + roleCode, Role.class);

        // THEN
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).extracting("id", "code").containsOnly(1010101L, roleCode);
    }

    @Test
    public void getRole_shouldReturn404_whenRoleDoesNotExist() {
        // GIVEN
        String roleCode = "UNKNOWN";

        // WHEN
        ResponseEntity<Object> actual = restTemplate.getForEntity(baseUrl + "/" + roleCode, Object.class);

        // WHEN THEN
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).extracting("error", "message")
                .containsOnly("Not Found", "Could not find role by code: " + roleCode);
    }

}
