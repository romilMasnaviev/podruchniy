package ru.xorochki.resSearch.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserRequest {

    @NotNull(message = "Username cannot be null")
    @Size(min = 1, max = 50, message = "Username must be between 1 and 50 characters")
    private String username;

    @Pattern(regexp = "(^$|[0-9]{11})", message = "Invalid mobile number")
    private String mobileNumber;

    @Email(message = "Invalid email address")
    private String email;

    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    private String password;
}
