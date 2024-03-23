package com.dong.do_an.controller;

import com.dong.do_an.constants.StatusCode;
import com.dong.do_an.dto.ChangePasswordDTO;
import com.dong.do_an.entity.SystemUser;
import com.dong.do_an.model.BaseResponse;
import com.dong.do_an.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("profile")
    public ResponseEntity getProfile(Authentication authentication) {
        final Optional<SystemUser> optionalSystemUser = userRepository.findById(authentication.getName());
        if (optionalSystemUser.isPresent()) {
            final SystemUser systemUser = optionalSystemUser.get();
            systemUser.setPassword(null);

            return ResponseEntity
                    .ok()
                    .body(
                            BaseResponse
                                    .builder()
                                    .code(StatusCode.SUCCESS)
                                    .data(systemUser)
                                    .build()
                    );
        } else {
            return ResponseEntity
                    .ok()
                    .body(
                            BaseResponse
                                    .builder()
                                    .code(StatusCode.NOT_FOUND)
                                    .build()
                    );
        }
    }


    @PostMapping("change_password")
    @Transactional
    public ResponseEntity changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, Authentication authentication) {
        if (changePasswordDTO.getNewPassword() == null || !changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
            return ResponseEntity
                    .ok()
                    .body(
                            BaseResponse
                                    .builder()
                                    .code(StatusCode.NOT_SAME_PASSWORD)
                                    .build()
                    );
        }

        final Optional<SystemUser> optionalSystemUser = userRepository.findById(authentication.getName());
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

        final SystemUser systemUser = optionalSystemUser.get();
        if (!systemUser.getPassword().equals(changePasswordDTO.getOldPassword())) {
            return ResponseEntity
                    .ok()
                    .body(
                            BaseResponse
                                    .builder()
                                    .code(StatusCode.WRONG_OLD_PASSWORD)
                                    .build()
                    );
        }

        systemUser.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
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
}
