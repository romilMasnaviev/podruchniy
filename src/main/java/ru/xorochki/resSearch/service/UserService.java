package ru.xorochki.resSearch.service;

import ru.xorochki.resSearch.dto.*;
import ru.xorochki.resSearch.model.User;

import java.util.List;

public interface UserService {

    User create(UserRequest request);

    void remove(Long userId);

    UserResponse update(UserUpdateRequest user, Long userId);

    List<UserResponse> getAll();

    UserResponse get(Long userId);

    void checkUserExist(LoginRequest loginRequest);


    Long getUserIdByUsername(String username);

    UserResponse getUserById(Long userId);

    List<RestaurantResponse> getUserFavorites(Long userId);
}
