package com.github.userservice.helpers;

import com.github.userservice.data.models.Role;
import com.github.userservice.data.models.User;
import com.github.userservice.views.UserRequest;

import java.util.Random;

public abstract class StubBuilder {

    private static Random random = new Random();

    public static User user() {
        return User.builder()
                .forename("Charles")
                .surname("Babbage")
                .email("test@email.co.uk")
                .build();
    }

    public static Long randomId() {
        return random.nextLong();
    }

    public static UserRequest userRequest() {
        return UserRequest.builder()
                .forename("Charles")
                .surname("Babbage")
                .email("test@email.co.uk")
                .build();
    }

    public static Role role() {
        return Role.builder()
                .code("ADMIN")
                .displayName("Administrator")
                .build();
    }

}
