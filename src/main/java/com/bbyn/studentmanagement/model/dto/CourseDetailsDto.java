package com.bbyn.studentmanagement.model.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CourseDetailsDto {
    private Long id;
    private String name;
    private String description;
    private int capacity;
    private List<CourseSessionDto> courseSessions;
    private List<StudentSimpleDto> students;
}

