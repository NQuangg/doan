package com.dong.do_an.entity;

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
    private Date birthDate;

    @Column(nullable = false)
    private String phoneNumber;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    private Classroom classroom;
}
