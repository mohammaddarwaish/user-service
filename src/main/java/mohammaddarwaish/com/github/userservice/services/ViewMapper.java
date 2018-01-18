package mohammaddarwaish.com.github.userservice.services;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import mohammaddarwaish.com.github.userservice.data.models.User;
import mohammaddarwaish.com.github.userservice.views.UserRequest;
import org.springframework.stereotype.Service;

@Service
public class ViewMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        registerUserClassMap(factory);
    }

    private void registerUserClassMap(final MapperFactory factory) {
        factory.classMap(User.class, UserRequest.class)
                .field("forename", "forename")
                .field("surname", "surname")
                .field("email", "email")
                .byDefault()
                .register();
    }

}
