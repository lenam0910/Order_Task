package com.Od_Tasking.exception;

import com.Od_Tasking.dto.Respone.ApiRespone;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.Od_Tasking.exception.ErrorCode.Existing_Username;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiRespone> handlingAppException(AppException appException) {
        ErrorCode errorCode= appException.getErrorCode();

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(
                ApiRespone.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiRespone> handlingValidation(MethodArgumentNotValidException methodArgumentNotValidException){
        String enumKey = methodArgumentNotValidException.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(
                ApiRespone.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiRespone> handleConstraintViolation(DataIntegrityViolationException ex) {
        Throwable cause = ex.getRootCause();
        if (cause instanceof PSQLException psqlEx) {
            // Nhận diện theo constraint name trong message
             ErrorCode errorCode = Existing_Username ;
            if (psqlEx.getMessage().contains("user_name")) {

                return ResponseEntity.status(errorCode.getHttpStatusCode()).body(
                        ApiRespone.builder()
                                .code(errorCode.getCode())
                                .message(errorCode.getMessage())
                                .build()
                );
            }
        }


        return null;
    }



}
