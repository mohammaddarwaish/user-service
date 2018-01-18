package mohammaddarwaish.com.github.userservice.services;

import lombok.AllArgsConstructor;
import mohammaddarwaish.com.github.userservice.data.models.User;
import mohammaddarwaish.com.github.userservice.data.repositories.UserRepository;
import mohammaddarwaish.com.github.userservice.views.UserRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EntityMapper entityMapper;

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find user by id: " + userId));
    }

    public User createUser(UserRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new EntityExistsException(String.format("User with email address %s already exists", request.getEmail()));
                });

        User user = mapToUser(request);
        return userRepository.save(user);
    }

    public void updateUser(Long userId, Map<String, Object> updatedFields) {
        User user = getUser(userId);
        UserRequest updatedEntity = entityMapper.mergeFieldsWithEntity(UserRequest.class, mapFromUser(user), updatedFields);
        User entity = mapToUser(updatedEntity);
        entity.setId(userId);
        userRepository.save(entity);
    }

    private User mapToUser(UserRequest request) {
        return User.builder()
                .forename(request.getForename())
                .surname(request.getSurname())
                .email(request.getEmail())
                .build();
    }

    private UserRequest mapFromUser(User user) {
        return UserRequest.builder()
                .forename(user.getForename())
                .surname(user.getSurname())
                .email(user.getEmail())
                .build();
    }

}
