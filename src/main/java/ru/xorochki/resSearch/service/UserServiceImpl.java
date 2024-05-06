package ru.xorochki.resSearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.xorochki.resSearch.dao.JpaUserRepository;
import ru.xorochki.resSearch.dto.UserConverter;
import ru.xorochki.resSearch.dto.UserRequest;
import ru.xorochki.resSearch.dto.UserResponse;
import ru.xorochki.resSearch.dto.UserUpdateRequest;
import ru.xorochki.resSearch.model.User;

import javax.validation.ValidationException;
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
}
