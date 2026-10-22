package com.cibertec.puestosservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PuestosServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PuestosServiceApplication.class, args);
    }
}
