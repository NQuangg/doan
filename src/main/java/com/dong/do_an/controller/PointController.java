package com.dong.do_an.controller;

import com.dong.do_an.constants.StatusCode;
import com.dong.do_an.dto.PointDTO;
import com.dong.do_an.entity.Semester;
import com.dong.do_an.entity.SemesterPoint;
import com.dong.do_an.entity.SemesterPointId;
import com.dong.do_an.entity.SubjectPoint;
import com.dong.do_an.model.BaseResponse;
import com.dong.do_an.repository.PointRepository;
import com.dong.do_an.repository.SemesterRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("point")
public class PointController {

    private PointRepository pointRepository;
    private SemesterRepository semesterRepository;

    @GetMapping("detail")
    @Transactional
    public ResponseEntity getDetailPoint(@RequestBody PointDTO pointDTO) {
        final SemesterPointId semesterPointId = new SemesterPointId();
        semesterPointId.setUserId(pointDTO.getUserId());
        semesterPointId.setSemesterId(pointDTO.getSemesterId());

        final Optional<SemesterPoint> optionalSemesterPoint = pointRepository.findById(semesterPointId);
        if (!optionalSemesterPoint.isPresent()) {
            final Optional<Semester> optionalSemester = semesterRepository.findById(pointDTO.getSemesterId());
            if (!optionalSemester.isPresent()) {
                return ResponseEntity
                        .ok()
                        .body(
                                BaseResponse
                                        .builder()
                                        .code(StatusCode.NOT_FOUND)
                                        .build()
                        );
            }

            Semester semester = optionalSemester.get();
            List<SubjectPoint> listSubjectPoint = semester.getListSubject().stream().map(item -> {
                final SubjectPoint subjectPoint = new SubjectPoint();
                subjectPoint.setSubjectName(item.getName());
                return subjectPoint;
            }).toList();

            final SemesterPoint semesterPoint = new SemesterPoint();
            semesterPoint.setSemesterPointId(semesterPointId);
            semesterPoint.setListSubjectPoint(listSubjectPoint);
            pointRepository.save(semesterPoint);
        }

        return ResponseEntity
                .ok()
                .body(
                        BaseResponse
                                .builder()
                                .code(StatusCode.SUCCESS)
                                .data(pointRepository.findById(semesterPointId))
                                .build()
                );
    }

    @PostMapping("update")
    @Transactional
    public ResponseEntity updatePoint(@RequestBody PointDTO pointDTO) {
        final SemesterPointId semesterPointId = new SemesterPointId();
        semesterPointId.setUserId(pointDTO.getUserId());
        semesterPointId.setSemesterId(pointDTO.getSemesterId());

        final Optional<SemesterPoint> optionalSemesterPoint = pointRepository.findById(semesterPointId);
        if (optionalSemesterPoint.isPresent()) {
            final SemesterPoint semesterPoint = optionalSemesterPoint.get();
            semesterPoint.setListSubjectPoint(pointDTO.getListSubjectPoint());
            pointRepository.save(semesterPoint);
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
