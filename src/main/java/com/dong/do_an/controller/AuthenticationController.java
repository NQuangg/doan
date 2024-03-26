package com.dong.do_an.controller;

import com.dong.do_an.constants.StatusCode;
import com.dong.do_an.dto.LoginDTO;
import com.dong.do_an.dto.RegisterUserDTO;
import com.dong.do_an.dto.ResetPasswordDTO;
import com.dong.do_an.dto.UpdateUserDTO;
import com.dong.do_an.entity.Classroom;
import com.dong.do_an.entity.Role;
import com.dong.do_an.entity.SystemUser;
import com.dong.do_an.model.BaseResponse;
import com.dong.do_an.repository.UserRepository;
import com.dong.do_an.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @PostMapping("register")
    @Transactional
    public ResponseEntity register(@RequestBody RegisterUserDTO registerUserDTO) {
        final SystemUser systemUser = new SystemUser();
        systemUser.setEmail(registerUserDTO.getEmail());
        systemUser.setName(registerUserDTO.getName());
        systemUser.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        systemUser.setBirthDate(registerUserDTO.getBirthDate());
        systemUser.setPhoneNumber(registerUserDTO.getPhoneNumber());
        systemUser.setIsFemale(registerUserDTO.getIsFemale());
        systemUser.setRole(Role.USER);

        final Classroom classroom = new Classroom();
        classroom.setId(registerUserDTO.getClassroomId());
        systemUser.setClassroom(classroom);

        userRepository.save(systemUser);

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
    public ResponseEntity resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
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
