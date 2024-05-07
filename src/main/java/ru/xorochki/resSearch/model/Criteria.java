package ru.xorochki.resSearch.model;

import jakarta.persistence.*;
import lombok.*;

import lombok.Getter;

@Getter
@Entity
@Table(name = "criteria")
@NoArgsConstructor
public class Criteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public Criteria(String name){
        this.name=name;
    }
}
