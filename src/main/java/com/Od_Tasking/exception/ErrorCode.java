package com.Od_Tasking.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(1001,"User not found", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1002,"userName phải có ít nhất 3 kí tự",HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1003,"sai định dạng email",HttpStatus.BAD_REQUEST),
    ERROR_USERNAME_OR_PASSWORD(1004,"thông tin ko chính xác",HttpStatus.BAD_REQUEST),
    Existing_Username(1005,"Username đã tồn tại",HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

}
