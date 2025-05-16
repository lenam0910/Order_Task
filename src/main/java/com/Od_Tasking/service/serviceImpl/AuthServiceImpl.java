package com.Od_Tasking.service.serviceImpl;


import com.Od_Tasking.dto.Request.LoginRequest;
import com.Od_Tasking.dto.Respone.LoginRespone;
import com.Od_Tasking.entity.User;
import com.Od_Tasking.exception.AppException;
import com.Od_Tasking.exception.ErrorCode;
import com.Od_Tasking.repository.UserRepository;
import com.Od_Tasking.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtServiceImpl jwtServiceImpl;
    private final AuthenticationManager authenticationManager;
    @Override
    public LoginRespone login(LoginRequest loginRequest)  {
        veryfiUsernamePassword(loginRequest);

        User user = userRepository.findByUserName(loginRequest.getUserName());
        var token = jwtServiceImpl.generateToken(user);
        return  LoginRespone.builder()
                .jwtToken(token)
                .authenticated(true)
                .build();

    }


    @Override
    public void veryfiUsernamePassword(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            throw new AppException(ErrorCode.ERROR_USERNAME_OR_PASSWORD);
        }
    }


}
