package com.github.userservice.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.userservice.data.models.Role;
import com.github.userservice.helpers.StubBuilder;
import com.github.userservice.services.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityNotFoundException;

@RunWith(SpringRunner.class)
@WebMvcTest(RoleController.class)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    private String roleCode = "ADMIN";

    @Test
    public void getRole_shouldReturnARole() throws Exception {
        // GIVEN
        Role role = StubBuilder.role();
        given(roleService.getRole(roleCode)).willReturn(role);

        // WHEN THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/role/" + roleCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value(role.getCode()));
    }

    @Test
    public void getRole_shouldReturn404_whenRoleIsNotFound() throws Exception {
        // GIVEN
        given(roleService.getRole(roleCode)).willThrow(new EntityNotFoundException());

        // WHEN THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/role/" + roleCode))
                .andExpect(status().isNotFound());
    }

}
