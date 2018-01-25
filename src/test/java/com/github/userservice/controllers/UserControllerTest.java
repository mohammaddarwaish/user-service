package com.github.userservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.userservice.data.models.User;
import com.github.userservice.helpers.StubBuilder;
import com.github.userservice.services.UserService;
import com.github.userservice.views.UserRequest;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private Long userId = StubBuilder.randomId();

    @Test
    public void getUser_ShouldReturnAUser() throws Exception {
        // GIVEN
        User user = StubBuilder.user();
        given(userService.getUser(userId)).willReturn(user);

        // WHEN THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value(user.getEmail()));
    }

    @Test
    public void getUser_ShouldReturn404_WhenUserIsNotFound() throws Exception {
        // GIVEN
        given(userService.getUser(userId)).willThrow(new EntityNotFoundException());

        // WHEN THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user/" + userId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void postUser_ShouldCreateAUser() throws Exception {
        // GIVEN
        User user = StubBuilder.user();
        user.setId(userId);
        UserRequest request = StubBuilder.userRequest();
        String payload = new ObjectMapper().writeValueAsString(request);

        given(userService.createUser(request)).willReturn(user);

        // GIVEN THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("email").value(user.getEmail()))
                .andExpect(header().string("Location","/v1/user/" + userId));
    }

    @Test
    public void postUser_ShouldReturn409_WhenUserWithEmailAlreadyExists() throws Exception {
        // GIVEN
        UserRequest request = StubBuilder.userRequest();
        String payload = new ObjectMapper().writeValueAsString(request);

        given(userService.createUser(request)).willThrow(new EntityExistsException());

        // GIVEN THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isConflict());
    }

    @Test
    public void patchUser_ShouldUpdateTheFieldsPassedInThePayload() throws Exception {
        // GIVEN
        Map<String, Object> request = ImmutableMap.of("email", "test@email.co.uk");
        String payload = new ObjectMapper().writeValueAsString(request);
        willDoNothing().given(userService).updateUser(userId, request);

        // WHEN THEN
        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/user/" + userId)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isNoContent());
        verify(userService).updateUser(userId, request);
    }
}