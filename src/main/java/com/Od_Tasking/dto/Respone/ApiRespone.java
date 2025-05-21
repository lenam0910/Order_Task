package com.Od_Tasking.dto.Respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiRespone<T> {
    int code;
    String message;
    T result;

}