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
public class UpdateUserDTO {
    private String email;

    private String name;

    private String password;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    private String phoneNumber;

    private String imageUrl;
}
