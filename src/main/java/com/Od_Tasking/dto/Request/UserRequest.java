package com.Od_Tasking.dto.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
    private String userName;
    private String password;
    private String email;

}
