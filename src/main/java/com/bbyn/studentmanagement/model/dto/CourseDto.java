package com.bbyn.studentmanagement.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CourseDto {
    private Long id;
    private String name;
    private String description;
    private int capacity;
}
