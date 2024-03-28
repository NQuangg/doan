package com.dong.do_an.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO {
    private String email;

    private String name;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    private String phoneNumber;

    private Boolean isFemale;

    private Integer classroomId;
}
