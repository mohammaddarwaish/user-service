package com.github.userservice.services;

import com.github.userservice.data.models.User;
import com.github.userservice.data.repositories.UserRepository;
import com.github.userservice.views.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EntityMapper entityMapper;
    private final ViewMapper viewMapper;

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find user by id: " + userId));
    }

    public User createUser(UserRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new EntityExistsException(String.format("User with email address %s already exists", request.getEmail()));
                });

        User user = viewMapper.map(request, User.class);
        return userRepository.save(user);
    }

    public void updateUser(Long userId, Map<String, Object> updatedFields) {
        User user = getUser(userId);
        UserRequest request = viewMapper.map(user, UserRequest.class);

        UserRequest updatedEntity = entityMapper.mergeFieldsWithEntity(UserRequest.class, request, updatedFields);

        User entity = viewMapper.map(updatedEntity, User.class);
        entity.setId(userId);

        userRepository.save(entity);
    }

    public void deleteUser(Long userId) {
        getUser(userId);
        userRepository.deleteById(userId);
    }

}
