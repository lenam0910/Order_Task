package com.Od_Tasking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data

@Document(collection = "users")
public class User extends UserBase {

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    private String id;

    @Column(unique = true)
    private String userName;
    private String password;
    private String email;
    private String role;
}
