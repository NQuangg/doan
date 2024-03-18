package com.dong.do_an.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SemesterPointId implements Serializable {
    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer semesterId;
}
