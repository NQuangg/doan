package com.dong.do_an.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemUser {
    @Id
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private Boolean isFemale;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    private Classroom classroom;
}
