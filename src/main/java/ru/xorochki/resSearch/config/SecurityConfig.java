package ru.xorochki.resSearch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import ru.xorochki.resSearch.security.AuthProviderImpl;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfiguration {
    private final AuthProviderImpl authProvider;
    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authProvider);//TODO
    }
}
