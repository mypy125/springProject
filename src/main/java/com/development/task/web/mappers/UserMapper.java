package com.development.task.web.mappers;

import com.development.task.domain.user.User;
import com.development.task.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {

}
