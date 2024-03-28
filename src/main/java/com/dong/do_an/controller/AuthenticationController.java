package com.dong.do_an.controller;

import com.dong.do_an.constants.StatusCode;
import com.dong.do_an.dto.LoginDTO;
import com.dong.do_an.dto.RegisterUserDTO;
import com.dong.do_an.dto.UserEmailDTO;
import com.dong.do_an.entity.Classroom;
import com.dong.do_an.entity.Role;
import com.dong.do_an.entity.SystemUser;
import com.dong.do_an.model.BaseResponse;
import com.dong.do_an.repository.UserRepository;
import com.dong.do_an.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JavaMailSender emailSender;

    @PostMapping("register")
    @Transactional
    public ResponseEntity register(@RequestBody RegisterUserDTO registerUserDTO) {
        final Optional<SystemUser> optionalSystemUser = userRepository.findById(registerUserDTO.getEmail());
        if (optionalSystemUser.isPresent()) {
            return ResponseEntity
                    .ok()
                    .body(
                            BaseResponse
                                    .builder()
                                    .code(StatusCode.EMAIL_EXISTED)
                                    .build()
                    );
        }

        final String newPassword = RandomStringUtils.randomAlphanumeric(6);
        System.out.println("new password: " + newPassword);

        final SystemUser systemUser = new SystemUser();
        systemUser.setEmail(registerUserDTO.getEmail());
        systemUser.setName(registerUserDTO.getName());
        systemUser.setPassword(passwordEncoder.encode(newPassword));
        systemUser.setBirthDate(registerUserDTO.getBirthDate());
        systemUser.setPhoneNumber(registerUserDTO.getPhoneNumber());
        systemUser.setIsFemale(registerUserDTO.getIsFemale());
        systemUser.setRole(Role.USER);

        final Classroom classroom = new Classroom();
        classroom.setId(registerUserDTO.getClassroomId());
        systemUser.setClassroom(classroom);

        userRepository.save(systemUser);

        final StringBuilder messageText = new StringBuilder();
        messageText
                .append("Tài khoản của bạn được kích hoạt thành công. \n")
                .append("Tên tài khoản: ")
                .append(registerUserDTO.getEmail())
                .append("\n")
                .append("Mật khẩu: ")
                .append(newPassword)
                .append("\n");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("${spring.mail.username}");
        message.setTo(registerUserDTO.getEmail());
        message.setSubject("TẠO TÀI KHOẢN THÀNH CÔNG");
        message.setText(messageText.toString());
        emailSender.send(message);

        return ResponseEntity
                .ok()
                .body(
                        BaseResponse
                                .builder()
                                .code(StatusCode.SUCCESS)
                                .build()
                );
    }

    @PostMapping("login")
    @Transactional
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getPassword()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity
                    .ok()
                    .body(
                            BaseResponse
                                    .builder()
                                    .code(StatusCode.AUTHENTICATION_FAILED)
                                    .build()
                    );
        }

        final Optional<SystemUser> optionalSystemUser = userRepository.findById(loginDTO.getEmail());
        if (optionalSystemUser.isEmpty()) {
            return ResponseEntity
                    .ok()
                    .body(
                            BaseResponse
                                    .builder()
                                    .code(StatusCode.NOT_FOUND)
                                    .build()
                    );
        }

        final String jwtToken = jwtService.generateToken(optionalSystemUser.get().getEmail());
        return ResponseEntity
                .ok()
                .body(
                        BaseResponse
                                .builder()
                                .code(StatusCode.SUCCESS)
                                .data(jwtToken)
                                .build()
                );
    }

    @PostMapping("reset_password")
    @Transactional
    public ResponseEntity resetPassword(@RequestBody UserEmailDTO userEmailDTO) {
        final Optional<SystemUser> optionalSystemUser = userRepository.findById(userEmailDTO.getEmail());
        if (optionalSystemUser.isEmpty()) {
            return ResponseEntity
                    .ok()
                    .body(
                            BaseResponse
                                    .builder()
                                    .code(StatusCode.EMAIL_NOT_EXISTED)
                                    .build()
                    );
        }

        final String newPassword = RandomStringUtils.randomAlphanumeric(6);
        System.out.println("new password: " + newPassword);

        final SystemUser systemUser = optionalSystemUser.get();
        systemUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(systemUser);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("${spring.mail.username}");
        message.setTo(userEmailDTO.getEmail());
        message.setSubject("RESET MẬT KHẨU");
        message.setText("Mật khẩu mới của bạn: " + newPassword);
        emailSender.send(message);
        return ResponseEntity
                .ok()
                .body(
                        BaseResponse
                                .builder()
                                .code(StatusCode.SUCCESS)
                                .build()
                );
    }
}
