package ru.xorochki.resSearch.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    private Long id;
    private String name;
    private Float rating;
    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviews;
    @Column(name = "cuisine")
    private CuisineType cuisineType;
    @Column(name = "price")
    private PriceRange priceRange;

    @ManyToMany
    @JoinTable(
            name = "restaurants_criteria",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "criteria_id")
    )
    private List<Criteria> criteria;
}
