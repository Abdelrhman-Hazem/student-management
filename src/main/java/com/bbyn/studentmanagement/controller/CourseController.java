package com.bbyn.studentmanagement.controller;

import com.bbyn.studentmanagement.mapper.CourseMapper;
import com.bbyn.studentmanagement.model.dto.CourseDetailsDto;
import com.bbyn.studentmanagement.model.dto.CourseDto;
import com.bbyn.studentmanagement.model.request.CourseCreationRequest;
import com.bbyn.studentmanagement.service.CourseService;
import com.bbyn.studentmanagement.util.PdfGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final CourseMapper courseMapper;

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody @Valid CourseCreationRequest courseCreationRequest) {
        CourseDto createdCourse = courseService.createCourse(courseCreationRequest);
        return ResponseEntity.ok(createdCourse);
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<CourseDto> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDetailsDto> getCourseById(@PathVariable Long courseId) {
        CourseDetailsDto courseDetails = courseService.getCourseById(courseId);
        return ResponseEntity.ok(courseDetails);
    }

    @GetMapping("/{courseId}/pdf")
    public ResponseEntity<byte[]> getCoursePdfById(@PathVariable Long courseId) throws Exception {
        CourseDetailsDto courseDetails = courseService.getCourseById(courseId);
        byte[] pdfBytes = PdfGenerator.generateCoursePdf(courseDetails);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "course_" + courseId + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}

