package com.Od_Tasking.service;

import com.Od_Tasking.entity.User;
import com.nimbusds.jose.JOSEException;


import java.text.ParseException;

public interface JwtService {
    String generateToken(User user);
    Boolean verify(String token) throws JOSEException, ParseException;
    String getSubject(String token);
}
