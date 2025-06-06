package com.Od_Tasking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OdTaskingApplication {

    public static void main(String[] args) {
        SpringApplication.run(OdTaskingApplication.class, args);
    }

}
