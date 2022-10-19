package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private Integer id;
    private String name;
    private String email;

    //c1: Query User => Convert to Dto
    //Convert tự viết
    //Sử dng dependence (mapstruct, modelMapper,...)

    //c2: sử dụng JPA query language
    //c3: sử dụng Projection
    //c4: Sử dụng native query
}
