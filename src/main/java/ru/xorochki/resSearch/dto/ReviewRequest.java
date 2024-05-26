package ru.xorochki.resSearch.dto;

import lombok.Data;

@Data
public class  ReviewRequest {
    private Long userId;
    private String username;
    private Long restaurantId;
    private String comment;
    private Float mark;
}
