package com.Od_Tasking.service.serviceImpl;


import com.Od_Tasking.dto.Request.UserRequest;
import com.Od_Tasking.dto.Respone.UserRespone;
import com.Od_Tasking.entity.User;
import com.Od_Tasking.exception.AppException;
import com.Od_Tasking.exception.ErrorCode;
import com.Od_Tasking.mapper.UserMapper;
import com.Od_Tasking.repository.UserRepository;
import com.Od_Tasking.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public UserRespone createUser(UserRequest userRequest) {
        log.info("Requst name is {}", userRequest.getUserName());

        User user = userMapper.toUser(userRequest);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(user);
        return userMapper.toRespone(user);
    }

    @Override
    public UserRespone updateUser(String id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper. userUpdate(user, userRequest);
//        user.setUpdated(true);
        userRepository.save(user);
        return userMapper.toRespone(user);
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
//        if(user==null)
//          throw new AppException(ErrorCode.USER_NOT_FOUND);
//        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public UserRespone searchUser(String id) {
        User user = userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toRespone(user);
    }

    @Override
    public List<UserRespone> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toRespone).collect(Collectors.toList());
    }
}
