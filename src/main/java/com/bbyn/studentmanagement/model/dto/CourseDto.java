package com.bbyn.studentmanagement.model.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CourseDto implements Serializable {
    private static final long serialVersionUID = 123432498L;

    private Long id;
    private String name;
    private String description;
    private int capacity;
}
