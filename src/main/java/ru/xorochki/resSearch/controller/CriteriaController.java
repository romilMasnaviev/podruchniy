package ru.xorochki.resSearch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import ru.xorochki.resSearch.dto.CriteriaResponse;
import ru.xorochki.resSearch.dto.RestaurantResponse;
import ru.xorochki.resSearch.service.CriteriaService;
import ru.xorochki.resSearch.service.RestaurantService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CriteriaController {

    private final CriteriaService criteriaService;

    @GetMapping("/criteria/search")
    public String search(Model model) {
        List<CriteriaResponse> criteriaList = criteriaService.getAllCriteria();
        model.addAttribute("criteriaList", criteriaList);
        return "search";
    }

    @GetMapping("/criteria/selected")
    public String getSelectedCriteria(@RequestParam(value = "selectedCriteria") List<Long> selectedCriteria, Model model) {
        if (selectedCriteria != null && !selectedCriteria.isEmpty()) {
            List<RestaurantResponse> restaurants = criteriaService.findAllByIds(selectedCriteria);
            model.addAttribute("restaurants", restaurants);
            return "search-results";
        }
        return null;
    }
}