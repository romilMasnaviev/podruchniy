package ru.xorochki.resSearch.dto;

import lombok.Data;
import ru.xorochki.resSearch.model.Restaurant;

@Data
public class ReviewResponse {
    private Long id;
    private Float mark;
    private String comment;
    private Restaurant restaurant;
}
