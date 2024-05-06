package ru.xorochki.resSearch.dto;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.xorochki.resSearch.model.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-06T11:17:57+0300",
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

    @Override
    public UserGetResponse UserConvertToUserGetResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserGetResponse userGetResponse = new UserGetResponse();

        userGetResponse.setId( user.getId() );
        userGetResponse.setUsername( user.getUsername() );
        userGetResponse.setMobileNumber( user.getMobileNumber() );
        userGetResponse.setEmail( user.getEmail() );
        userGetResponse.setPassword( user.getPassword() );

        return userGetResponse;
    }

    @Override
    public List<UserGetResponse> UserConvertToUserGetResponse(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserGetResponse> list = new ArrayList<UserGetResponse>( users.size() );
        for ( User user : users ) {
            list.add( UserConvertToUserGetResponse( user ) );
        }

        return list;
    }
}
