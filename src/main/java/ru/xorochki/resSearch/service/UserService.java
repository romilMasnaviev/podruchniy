package ru.xorochki.resSearch.service;

import ru.xorochki.resSearch.dto.UserRequest;
import ru.xorochki.resSearch.dto.UserResponse;
import ru.xorochki.resSearch.dto.UserUpdateRequest;
import ru.xorochki.resSearch.model.User;

import java.util.List;

public interface UserService {

    User create(UserRequest request);

    void remove(Long userId);

    UserResponse update(UserUpdateRequest user, Long userId);

    List<UserResponse> getAll();

    UserResponse get(Long userId);
}
