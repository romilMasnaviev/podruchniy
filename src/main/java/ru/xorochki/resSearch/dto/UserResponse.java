package ru.xorochki.resSearch.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String mobileNumber;
    private String email;
    private String password;
}
