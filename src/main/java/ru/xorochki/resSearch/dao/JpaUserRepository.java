package ru.xorochki.resSearch.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xorochki.resSearch.model.User;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Long> {

    //boolean existsByUsernameAndPassword(String userName, String password);

    Optional<User> findByUsername(String username);

}
