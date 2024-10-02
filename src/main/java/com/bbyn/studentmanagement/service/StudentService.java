package com.bbyn.studentmanagement.service;

import com.bbyn.studentmanagement.model.dto.CourseDto;
import com.bbyn.studentmanagement.model.dto.StudentDto;

import java.util.List;

public interface StudentService {

    StudentDto registerStudent(StudentDto studentDto);

    StudentDto authenticate(StudentDto studentDto);

    void enrollStudentToCourse(Long courseId, Long studentId);

    void withdrawStudentToCourse(Long courseId, Long studentId);

    List<CourseDto> getStudentCourses(Long studentId);
}

