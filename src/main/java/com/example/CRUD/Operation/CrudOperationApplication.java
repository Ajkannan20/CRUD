package com.example.CRUD.Operation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.CRUD")
public class CrudOperationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudOperationApplication.class, args);
    }
}
