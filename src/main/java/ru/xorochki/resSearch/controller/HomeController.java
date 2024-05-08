package ru.xorochki.resSearch.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.xorochki.resSearch.security.UserDetailsMy;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String getHome() {
        return "home";
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsMy userDetailsMy = (UserDetailsMy) authentication.getPrincipal();
        System.out.println(userDetailsMy);
        return "home";
    }
}
