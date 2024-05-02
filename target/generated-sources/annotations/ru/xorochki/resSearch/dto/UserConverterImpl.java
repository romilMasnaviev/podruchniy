package ru.xorochki.resSearch.dto;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.xorochki.resSearch.model.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-02T15:45:00+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class UserConverterImpl implements UserConverter {

    @Override
    public User UserRequestConvertToUser(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( userRequest.getUsername() );
        user.setMobileNumber( userRequest.getMobileNumber() );
        user.setEmail( userRequest.getEmail() );
        user.setPassword( userRequest.getPassword() );

        return user;
    }
}
