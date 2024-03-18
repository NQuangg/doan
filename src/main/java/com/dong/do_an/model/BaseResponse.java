package com.dong.do_an.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse <T>{
    private String code;
    private T data;
}
