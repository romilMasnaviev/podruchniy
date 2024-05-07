package ru.xorochki.resSearch.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xorochki.resSearch.model.User;

public interface JpaUserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameAndPassword(String userName,String password);
}
