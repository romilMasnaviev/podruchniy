package ru.xorochki.resSearch.dto;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String username;
    private String mobileNumber;
    private String email;
    private String password;
}
