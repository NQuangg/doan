package com.dong.do_an.exception;

import com.dong.do_an.constants.StatusCode;
import com.dong.do_an.model.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity handleAllException(RuntimeException ex) {
        return ResponseEntity
                .ok()
                .body(
                        BaseResponse
                                .builder()
                                .code(StatusCode.SERVER_ERROR)
                                .build()
                );
    }
}
