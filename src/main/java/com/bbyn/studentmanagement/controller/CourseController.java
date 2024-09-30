package com.bbyn.studentmanagement.controller;

import com.bbyn.studentmanagement.model.dto.CourseDetailsDto;
import com.bbyn.studentmanagement.model.dto.CourseDto;
import com.bbyn.studentmanagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto) {
        CourseDto createdCourse = courseService.createCourse(courseDto);
        return ResponseEntity.ok(createdCourse);
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<CourseDto> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDetailsDto> getCourseById(@PathVariable Long courseId) {
        CourseDetailsDto course = courseService.getCourseById(courseId);
        return ResponseEntity.ok(course);
    }
}

