package com.bbyn.studentmanagement.controller;


import com.bbyn.studentmanagement.model.dto.CourseDto;
import com.bbyn.studentmanagement.model.dto.StudentDto;
import com.bbyn.studentmanagement.model.request.StudentRegisterRequest;
import com.bbyn.studentmanagement.model.response.AuthResponse;
import com.bbyn.studentmanagement.model.request.StudentLoginRequest;
import com.bbyn.studentmanagement.service.StudentService;
import com.bbyn.studentmanagement.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid StudentRegisterRequest request) {
        StudentDto studentDto = studentService.registerStudent(StudentDto.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build()
        );
        String token = jwtUtil.generateToken(studentDto.getId().toString());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid StudentLoginRequest request) {
        StudentDto studentDto = studentService.authenticate(StudentDto.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build()
        );
        String token = jwtUtil.generateToken(studentDto.getId().toString());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/enroll/{courseId}")
    public ResponseEntity<String> enrollToCourse(@PathVariable Long courseId, @AuthenticationPrincipal String studentId) {
        studentService.enrollStudentToCourse(courseId, Long.parseLong(studentId));
        return ResponseEntity.ok("Enrolled to course successfully");
    }

    @GetMapping("/courses")
    public ResponseEntity<List<CourseDto>> getStudentCourses(@AuthenticationPrincipal String studentId) {
        List<CourseDto> courses = studentService.getStudentCourses(Long.parseLong(studentId));
        return ResponseEntity.ok(courses);
    }
}
