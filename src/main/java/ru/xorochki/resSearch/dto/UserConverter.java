package ru.xorochki.resSearch.dto;

import org.mapstruct.Mapper;
import ru.xorochki.resSearch.model.User;

@Mapper(componentModel = "spring")
public interface UserConverter {

    User UserRequestConvertToUser(UserRequest userRequest);
}
