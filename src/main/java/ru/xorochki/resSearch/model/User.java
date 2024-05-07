package ru.xorochki.resSearch.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String username;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Email
    private String email;
    @OneToMany(mappedBy = "owner")
    private List<Review> reviews;
    private String password;
}
