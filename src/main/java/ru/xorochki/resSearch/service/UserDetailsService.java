package ru.xorochki.resSearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.xorochki.resSearch.dao.JpaUserRepository;
import ru.xorochki.resSearch.model.User;
import ru.xorochki.resSearch.security.UserDetailsMy;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final JpaUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUsername(username);
        if (user.isEmpty()) throw new UsernameNotFoundException("User not found");
        return new UserDetailsMy(user.get());
    }

}
