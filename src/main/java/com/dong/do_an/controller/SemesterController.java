package com.dong.do_an.controller;

import com.dong.do_an.constants.StatusCode;
import com.dong.do_an.dto.DetailDTO;
import com.dong.do_an.entity.Semester;
import com.dong.do_an.entity.SystemUser;
import com.dong.do_an.model.BaseResponse;
import com.dong.do_an.repository.SemesterRepository;
import com.dong.do_an.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("semester")
@RequiredArgsConstructor
public class SemesterController {

    final private UserRepository userRepository;
    final private SemesterRepository repository;

    @GetMapping("listForAdmin")
    public ResponseEntity getListForAdmin() {
        return ResponseEntity
                .ok()
                .body(
                        BaseResponse
                                .builder()
                                .code(StatusCode.SUCCESS)
                                .data(repository.findAll())
                                .build()
                );
    }

    @GetMapping("listForUser")
    public ResponseEntity getListForUser(Authentication authentication) {
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
        return ResponseEntity
                .ok()
                .body(
                        BaseResponse
                                .builder()
                                .code(StatusCode.SUCCESS)
                                .data(systemUser.getClassroom().getListSemester())
                                .build()
                );
    }

    @GetMapping("detail")
    public ResponseEntity getDetailSemester(@RequestBody DetailDTO detailDTO) {
        return ResponseEntity
                .ok()
                .body(
                        BaseResponse
                                .builder()
                                .code(StatusCode.SUCCESS)
                                .data(repository.findById(detailDTO.getId()))
                                .build()
                );
    }

    @PostMapping("create")
    @Transactional
    public ResponseEntity createSemester(@RequestBody Semester semester) {
        repository.save(semester);
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
