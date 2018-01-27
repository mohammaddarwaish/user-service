package com.github.userservice.services;

import com.github.userservice.data.models.Role;
import com.github.userservice.data.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRole(String code) {
        return roleRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Could not find role by code: " + code));
    }

}
