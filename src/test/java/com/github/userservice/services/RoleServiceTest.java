package com.github.userservice.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

import com.github.userservice.data.models.Role;
import com.github.userservice.data.repositories.RoleRepository;
import com.github.userservice.helpers.StubBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    private String roleCode = "ADMIN";

    @Test
    public void getRole_shouldReturnARole() {
        // GIVEN
        Role role = StubBuilder.role();
        given(roleRepository.findByCode(roleCode)).willReturn(Optional.of(role));

        // WHEN
        Role actual = roleService.getRole(roleCode);

        // THEN
        assertThat(actual).isEqualTo(role);
    }

    @Test
    public void getRole_shouldThrowException_whenRoleIsNotFoundByCode() {
        // GIVEN
        given(roleRepository.findByCode(roleCode)).willReturn(Optional.empty());

        // WHEN
        Throwable actual = catchThrowable(() -> roleService.getRole(roleCode));

        // THEN
        assertThat(actual).isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Could not find role by code: " + roleCode);
    }

}
