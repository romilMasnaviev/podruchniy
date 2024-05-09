package ru.xorochki.resSearch.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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

    @ElementCollection
    @CollectionTable(name = "user_favorites", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "restaurant_id")
    private List<Long> favorites;

    private String password;
}
