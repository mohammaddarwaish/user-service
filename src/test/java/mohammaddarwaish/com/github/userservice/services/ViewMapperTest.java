package mohammaddarwaish.com.github.userservice.services;

import mohammaddarwaish.com.github.userservice.data.models.User;
import mohammaddarwaish.com.github.userservice.helpers.StubBuilder;
import mohammaddarwaish.com.github.userservice.views.UserRequest;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ViewMapperTest {

    private ViewMapper viewMapper;

    @Before
    public void setUp() throws Exception {
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