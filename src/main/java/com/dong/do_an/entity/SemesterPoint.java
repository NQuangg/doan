package com.dong.do_an.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SemesterPoint {
    @EmbeddedId
    private SemesterPointId semesterPointId;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date startDate;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date endDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumns({
            @JoinColumn(name = "userId"),
            @JoinColumn(name = "semesterId")
    })
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<SubjectPoint> listSubjectPoint;
}
