package com.dong.do_an.controller;

import com.dong.do_an.constants.StatusCode;
import com.dong.do_an.entity.Notification;
import com.dong.do_an.model.BaseResponse;
import com.dong.do_an.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository repository;

    @GetMapping("list")
    public ResponseEntity getListNotification() {
        return ResponseEntity
                .ok()
                .body(repository.findAll());
    }

    @PostMapping("create")
    @Transactional
    public ResponseEntity createNotification(@RequestBody Notification notification) {
        repository.save(notification);
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
