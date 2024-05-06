package ru.xorochki.resSearch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.xorochki.resSearch.dto.UserGetResponse;
import ru.xorochki.resSearch.dto.UserRequest;
import ru.xorochki.resSearch.model.User;
import ru.xorochki.resSearch.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User create(@RequestBody UserRequest request) {
        return userService.create(request);
    }

    @GetMapping
    public List<UserGetResponse> get() {
        return userService.getAll();
    }

    @PatchMapping("/{userId}")
    public User update(@PathVariable Long userId, @RequestBody User user) {
        return userService.update(user, userId);
    }

    @DeleteMapping("/{userId}")
    public void remove(@PathVariable Long userId) {
        userService.remove(userId);
    }
}
