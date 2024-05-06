package ru.xorochki.resSearch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.xorochki.resSearch.dto.UserRequest;
import ru.xorochki.resSearch.dto.UserResponse;
import ru.xorochki.resSearch.dto.UserUpdateRequest;
import ru.xorochki.resSearch.model.User;
import ru.xorochki.resSearch.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping
    public User create(@RequestBody @Valid UserRequest request) {
        return userService.create(request);
    }

    @GetMapping
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{userId}")
    public UserResponse get(@PathVariable Long userId) {
        return userService.get(userId);
    }

    @PatchMapping("/{userId}")
    public UserResponse update(@PathVariable Long userId, @RequestBody UserUpdateRequest user) {
        return userService.update(user, userId);
    }

    @DeleteMapping("/{userId}")
    public void remove(@PathVariable Long userId) {
        userService.remove(userId);
    }
}
