package ru.xorochki.resSearch.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xorochki.resSearch.model.Restaurant;

public interface JpaRestaurantRepository extends JpaRepository<Restaurant, Long> {

}
