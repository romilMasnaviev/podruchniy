package ru.xorochki.resSearch.service;

import ru.xorochki.resSearch.dto.UserRequest;

import ru.xorochki.resSearch.model.Restaurant;
import ru.xorochki.resSearch.model.User;

public interface UserService {
    User findById(Long userId);

    User create(UserRequest request);

    void remove(Long userId);

    User update(User user, Long userId);
}
