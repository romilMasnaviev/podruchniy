package ru.xorochki.resSearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.xorochki.resSearch.dao.JpaUserRepository;
import ru.xorochki.resSearch.dto.*;
import ru.xorochki.resSearch.model.User;

import jakarta.validation.ValidationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JpaUserRepository repository;
    private final UserConverter converter;

    @Override
    public void remove(Long userId) {
        if (!repository.existsById(userId)) {
            throw new ValidationException("User doesn't exist");
        }
        repository.deleteById(userId);
    }

    @Override
    public UserResponse update(UserUpdateRequest userUpdateDto, Long userId) {
        User existingUser = repository.findById(userId)
                .orElseThrow(() -> new ValidationException("User doesn't exist"));

        User updatedUser = updateUserFields(userUpdateDto, existingUser);

        return converter.UserConvertToUserGetResponse(repository.save(updatedUser));
    }

    @Override
    public List<UserResponse> getAll() {
        return converter.UserConvertToUserGetResponse(repository.findAll());
    }

    @Override
    public UserResponse get(Long userId) {
        return converter.UserConvertToUserGetResponse(repository.findById(userId).orElseThrow());
    }

    @Override
    public void checkUserExist(LoginRequest loginRequest) {
        if (!repository.existsByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword())) {
            throw new ValidationException("Не существует такого пользователя");
        }
    }

    @Override
    public User create(UserRequest request) {
        try {
            User user = converter.UserRequestConvertToUser(request);
            return repository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user: " + e.getMessage());
        }
    }

    private User updateUserFields(UserUpdateRequest userUpdateDto, User user) {
        if (userUpdateDto.getUsername() != null) {
            user.setUsername(userUpdateDto.getUsername());
        }
        if (userUpdateDto.getMobileNumber() != null) {
            user.setMobileNumber(userUpdateDto.getMobileNumber());
        }
        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getPassword() != null) {
            user.setPassword(userUpdateDto.getPassword());
        }
        return user;
    }

    public Long getUserIdByUsername(String username) {
        // Ваша логика для получения идентификатора пользователя по имени пользователя
        User user = repository.findByUsername(username).orElseThrow();
        return user.getId();
    }

    public UserResponse getUserById(Long userId) {
        User user = repository.findById(userId).orElse(null);
        if (user != null) {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setMobileNumber(user.getMobileNumber());
            userResponse.setEmail(user.getEmail());
            userResponse.setPassword(user.getPassword());
            return userResponse;
        } else {
            return null;
        }
    }
}
