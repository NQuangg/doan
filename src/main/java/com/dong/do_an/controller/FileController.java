package com.dong.do_an.controller;

import com.dong.do_an.constants.StatusCode;
import com.dong.do_an.model.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
@RequestMapping("file")
@RequiredArgsConstructor
public class FileController {

    @Value("${upload.folder}")
    private String uploadFolder;

    @PostMapping("upload")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

            Path path = Paths.get(uploadFolder + fileName);
            Files.write(path, file.getBytes());

            return ResponseEntity
                    .ok()
                    .body(
                            BaseResponse
                                    .builder()
                                    .code(StatusCode.SUCCESS)
                                    .data(fileName)
                                    .build()
                    );
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity
                    .ok()
                    .body(
                            BaseResponse
                                    .builder()
                                    .code(StatusCode.FAILED)
                                    .build()
                    );
        }
    }

    @GetMapping(
            produces = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE }
    )
    public ResponseEntity getFile(@RequestParam("fileName") String fileName) throws IOException {
        Path path = Paths.get(uploadFolder + fileName);
        return new ResponseEntity<>(Files.readAllBytes(path), HttpStatus.OK);
    }
}
