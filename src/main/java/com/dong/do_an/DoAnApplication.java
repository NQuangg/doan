package com.dong.do_an;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class DoAnApplication {
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void test() {
        System.out.println("QUANGG TEST PASSWORD: " + passwordEncoder.encode("123456"));
    }

    public static void main(String[] args) {
        SpringApplication.run(DoAnApplication.class, args);
    }

}
