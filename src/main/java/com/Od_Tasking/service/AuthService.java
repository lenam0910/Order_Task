package com.Od_Tasking.service;

import com.Od_Tasking.dto.Request.LoginRequest;
import com.Od_Tasking.dto.Respone.LoginRespone;
import com.nimbusds.jose.KeyLengthException;


public interface AuthService {
    public LoginRespone login(LoginRequest loginRequest) throws KeyLengthException;
    public void veryfiUsernamePassword(LoginRequest loginRequest);

}
