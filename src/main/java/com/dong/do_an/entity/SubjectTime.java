package com.dong.do_an.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectTime {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private Integer type;

    @Column(nullable = false)
    private String teacherName;

    @Column(nullable = false)
    private String className;

    @Column(nullable = false)
    private Integer week;
}
