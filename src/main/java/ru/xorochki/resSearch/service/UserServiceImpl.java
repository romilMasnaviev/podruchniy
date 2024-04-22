package ru.xorochki.resSearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.xorochki.resSearch.dao.JpaUserRepository;
import ru.xorochki.resSearch.dto.UserConverter;
import ru.xorochki.resSearch.dto.UserRequest;
import ru.xorochki.resSearch.model.Restaurant;
import ru.xorochki.resSearch.model.User;

import javax.validation.ValidationException;
import java.util.Optional;

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
    public User findById(Long userId) {
        Optional<User> userOptional = repository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ValidationException("User doesn't exist");
        }
        return userOptional.get();
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
}
