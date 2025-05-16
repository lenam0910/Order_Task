package com.Od_Tasking.dto.Respone;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class LoginRespone {
    String jwtToken;
    boolean authenticated;
}