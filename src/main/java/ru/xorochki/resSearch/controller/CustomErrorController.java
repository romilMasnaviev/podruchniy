package ru.xorochki.resSearch.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("errorMessage", "Что-то пошло не так. Пожалуйста, обратитесь к администратору.");
        return "error";
    }

    public String getErrorPath() {
        return "/error";
    }
}
