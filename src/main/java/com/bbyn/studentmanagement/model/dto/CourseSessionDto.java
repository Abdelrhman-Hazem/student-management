package com.bbyn.studentmanagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CourseSessionDto {
    private String dayOfWeek;
    private String startTime;
    private String endTime;
}
