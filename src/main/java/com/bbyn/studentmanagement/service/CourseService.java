package com.bbyn.studentmanagement.service;

import com.bbyn.studentmanagement.model.dto.CourseDetailsDto;
import com.bbyn.studentmanagement.model.dto.CourseDto;
import com.bbyn.studentmanagement.model.request.CourseCreationRequest;

import java.util.List;

public interface CourseService {

    CourseDto createCourse(CourseCreationRequest courseCreationRequest);

    List<CourseDto> getAllCourses();

    CourseDetailsDto getCourseById(Long courseId);
}

