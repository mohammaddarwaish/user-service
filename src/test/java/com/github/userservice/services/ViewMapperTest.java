package com.github.userservice.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.userservice.data.models.User;
import com.github.userservice.helpers.StubBuilder;
import com.github.userservice.views.UserRequest;
import org.junit.Before;
import org.junit.Test;

public class ViewMapperTest {

    private ViewMapper viewMapper;

    @Before
    public void setUp() {
        viewMapper = new ViewMapper();
    }

    @Test
    public void map_ShouldMapUserRequestToUser() {
        // GIVEN
        UserRequest request = StubBuilder.userRequest();

        // WHEN
        User user = viewMapper.map(request, User.class);

        // THEN
        assertThat(user).isNotNull().isEqualTo(StubBuilder.user());
    }

    @Test
    public void map_ShouldMapUserToUserRequest() {
        // GIVEN
        User user = StubBuilder.user();

        // WHEN
        UserRequest request = viewMapper.map(user, UserRequest.class);

        // THEN
        assertThat(request).isNotNull().isEqualTo(StubBuilder.userRequest());
    }

}