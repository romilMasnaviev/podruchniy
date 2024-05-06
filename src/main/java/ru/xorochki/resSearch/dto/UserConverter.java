package ru.xorochki.resSearch.dto;

import org.mapstruct.Mapper;
import ru.xorochki.resSearch.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserConverter {

    User UserRequestConvertToUser(UserRequest userRequest);

    UserGetResponse UserConvertToUserGetResponse(User user);

    List<UserGetResponse> UserConvertToUserGetResponse(List<User> users);
}
