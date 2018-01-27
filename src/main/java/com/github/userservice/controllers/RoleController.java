package com.github.userservice.controllers;

import com.github.userservice.data.models.Role;
import com.github.userservice.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/v1/role"})
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping(path = {"/{code}"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Role getRole(@PathVariable String code) {
        return this.roleService.getRole(code);
    }

}
