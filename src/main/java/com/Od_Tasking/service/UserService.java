package com.Od_Tasking.service;



import com.Od_Tasking.dto.Request.UserRequest;
import com.Od_Tasking.dto.Respone.UserRespone;

import java.util.List;

public interface UserService {
    UserRespone createUser(UserRequest userRequest);
    UserRespone updateUser(String id, UserRequest userRequest);
    void deleteUser(String id);
    UserRespone searchUser(String id);
    List<UserRespone> getAllUser();
}
