package com.bbyn.studentmanagement.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StudentDto {
    private Long id;
    private String username;
    private String password;
}
