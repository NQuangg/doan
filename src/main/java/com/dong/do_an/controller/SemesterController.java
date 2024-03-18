package com.dong.do_an.controller;

import com.dong.do_an.constants.StatusCode;
import com.dong.do_an.dto.DetailDTO;
import com.dong.do_an.entity.Semester;
import com.dong.do_an.model.BaseResponse;
import com.dong.do_an.repository.SemesterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("subject")
@RequiredArgsConstructor
public class SemesterController {

    private SemesterRepository repository;

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
