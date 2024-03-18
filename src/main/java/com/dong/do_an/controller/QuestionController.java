package com.dong.do_an.controller;

import com.dong.do_an.constants.StatusCode;
import com.dong.do_an.dto.QuestionDTO;
import com.dong.do_an.entity.Question;
import com.dong.do_an.model.BaseResponse;
import com.dong.do_an.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionRepository repository;

    @GetMapping("list")
    public ResponseEntity getListQuestion() {
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

    @PostMapping("create")
    @Transactional
    public ResponseEntity createQuestion(@RequestBody QuestionDTO questionDTO) {
        final Question question = new Question();
        question.setQuestion(questionDTO.getQuestion());

        repository.save(question);
        return ResponseEntity
                .ok()
                .body(
                        BaseResponse
                                .builder()
                                .code(StatusCode.SUCCESS)
                                .build()
                );
    }

    @PostMapping("answer")
    @Transactional
    public ResponseEntity answerQuestion(@RequestBody QuestionDTO questionDTO) {
        final Optional<Question> optionalQuestion = repository.findById(questionDTO.getId());
        if (optionalQuestion.isPresent()) {
            final Question question = optionalQuestion.get();
            question.setAnswer(questionDTO.getAnswer());
            repository.save(question);
            return ResponseEntity
                    .ok()
                    .body(
                            BaseResponse
                                    .builder()
                                    .code(StatusCode.SUCCESS)
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
}
