package com.bbyn.studentmanagement.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CourseCreationRequest {
    @NotBlank
    private String name;
    private String description;
    @NotNull
    @Min(value = 3, message = "Course Capacity can't be less than a 3")
    @Max(value = 100, message = "Course Capacity can't be more than a 100")
    private Integer capacity;
    @NotNull
    @NotEmpty
    private List<CourseSessionRequest> courseSessions;
}
