package com.Od_Tasking.mapper;


import com.Od_Tasking.dto.Request.UserRequest;
import com.Od_Tasking.dto.Respone.UserRespone;
import com.Od_Tasking.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserRespone toRespone(User user);
    UserRequest toRequest(User user);
    User toUser(UserRequest userRequest);
    void userUpdate(@MappingTarget User user, UserRequest userRequest);
}
