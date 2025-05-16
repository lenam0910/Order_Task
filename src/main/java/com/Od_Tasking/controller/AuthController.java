package com.Od_Tasking.controller;

import com.Od_Tasking.dto.Request.LoginRequest;
import com.Od_Tasking.dto.Respone.ApiRespone;
import com.Od_Tasking.dto.Respone.LoginRespone;
import com.Od_Tasking.service.AuthService;
import com.nimbusds.jose.KeyLengthException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("/login")
    public ApiRespone<Object> login(@RequestBody LoginRequest loginRequest) throws KeyLengthException {

        return ApiRespone.builder()
                .code(1000)
                .message("Login successful")
                .result(authService.login(loginRequest))
                .build();
    }

    @PostMapping("/login-cookie")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws KeyLengthException {
        LoginRespone loginRespone = authService.login(loginRequest);
        String token = loginRespone.getJwtToken();
        // ✅ Set token vào HttpOnly Cookie
        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .path("/")
                .maxAge(60 * 60 * 10) // 10h
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok("Đăng nhập thành công");
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0) // xoá
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok("Đăng xuất thành công");
    }
}
