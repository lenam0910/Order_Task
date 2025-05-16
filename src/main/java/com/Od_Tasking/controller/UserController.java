package com.Od_Tasking.controller;


import com.Od_Tasking.dto.Request.UserRequest;
import com.Od_Tasking.dto.Respone.ApiRespone;
import com.Od_Tasking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    ApiRespone<Object> createrUser(@RequestBody UserRequest userRequest){
        return  ApiRespone.builder()
                .code(1000)
                .message("Creted User")
                .result(userService.createUser(userRequest))
                .build();
    }
    @PutMapping("/{id}")
    ApiRespone<Object> updateUser(@PathVariable String id, @RequestBody UserRequest userRequest){
        return ApiRespone.builder()
                .code(1000)
                .message("Updated User")
                .result(userService.updateUser(id,userRequest))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiRespone<Object> deleteUser(@PathVariable String id){
        return  ApiRespone.builder()
                .code(1000)
                .message("Deleted User")
                .build();
    }

    @GetMapping("/{id}")
    ApiRespone<Object> getUserById(@PathVariable String id){
        return ApiRespone.builder()
                .code(1000)
                .message("Get User by id")
                .result(userService.searchUser(id))
                .build();
    }

    @GetMapping
    ApiRespone<Object> getAllUser(){
        return ApiRespone.builder()
                .code(1000)
                .message("Get all User")
                .result(userService.getAllUser())
                .build();
    }

}
