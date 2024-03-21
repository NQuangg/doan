package com.dong.do_an;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class DoAnApplication {
    public static void main(String[] args) {
        SpringApplication.run(DoAnApplication.class, args);
    }
}
