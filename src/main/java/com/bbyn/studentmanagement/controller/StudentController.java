package com.bbyn.studentmanagement.controller;


import com.bbyn.studentmanagement.model.dto.CourseDto;
import com.bbyn.studentmanagement.model.dto.StudentDto;
import com.bbyn.studentmanagement.model.request.StudentRegisterRequest;
import com.bbyn.studentmanagement.model.response.AuthResponse;
import com.bbyn.studentmanagement.model.request.StudentLoginRequest;
import com.bbyn.studentmanagement.service.StudentService;
import com.bbyn.studentmanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<String> enrollToCourse(@PathVariable Long courseId, @AuthenticationPrincipal Long studentId) {
        studentService.enrollStudentToCourse(courseId, studentId);
        return ResponseEntity.ok("Enrolled to course successfully");
    }

    @PostMapping("/withdraw/{courseId}")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<String> withdrawToCourse(@PathVariable Long courseId, @AuthenticationPrincipal Long studentId) {
        studentService.withdrawStudentToCourse(courseId, studentId);
        return ResponseEntity.ok("Withdrawn from course successfully");
    }

    @GetMapping("/courses")
    public ResponseEntity<List<CourseDto>> getStudentCourses(@AuthenticationPrincipal Long studentId) {
        List<CourseDto> courses = studentService.getStudentCourses(studentId);
        return ResponseEntity.ok(courses);
    }
}
