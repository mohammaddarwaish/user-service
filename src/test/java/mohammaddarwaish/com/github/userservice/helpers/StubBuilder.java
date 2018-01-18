package mohammaddarwaish.com.github.userservice.helpers;

import mohammaddarwaish.com.github.userservice.data.models.User;
import mohammaddarwaish.com.github.userservice.views.UserRequest;

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
}
