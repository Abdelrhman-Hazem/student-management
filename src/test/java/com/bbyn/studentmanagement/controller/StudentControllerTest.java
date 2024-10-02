package com.bbyn.studentmanagement.controller;

import com.bbyn.studentmanagement.model.dto.CourseDto;
import com.bbyn.studentmanagement.model.dto.StudentDto;
import com.bbyn.studentmanagement.model.request.StudentLoginRequest;
import com.bbyn.studentmanagement.model.request.StudentRegisterRequest;
import com.bbyn.studentmanagement.model.response.AuthResponse;
import com.bbyn.studentmanagement.service.StudentService;
import com.bbyn.studentmanagement.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private StudentController studentController;

    // Test for register method
    @Test
    public void testRegister_WhenValidRequest_ShouldReturnToken() {
        // Arrange
        StudentRegisterRequest request = new StudentRegisterRequest("user1", "password");
        StudentDto studentDto = new StudentDto(1L, "user1", "password");
        String token = "jwt-token";

        when(studentService.registerStudent(any(StudentDto.class))).thenReturn(studentDto);
        when(jwtUtil.generateToken(anyString())).thenReturn(token);

        // Act
        ResponseEntity<AuthResponse> response = studentController.register(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new AuthResponse(token), response.getBody());
        verify(studentService, times(1)).registerStudent(any(StudentDto.class));
        verify(jwtUtil, times(1)).generateToken(anyString());
    }

    // Test for login method
    @Test
    public void testLogin_WhenValidRequest_ShouldReturnToken() {
        // Arrange
        StudentLoginRequest request = new StudentLoginRequest("user1", "password");
        StudentDto studentDto = new StudentDto(1L, "user1", "password");
        String token = "jwt-token";

        when(studentService.authenticate(any(StudentDto.class))).thenReturn(studentDto);
        when(jwtUtil.generateToken(anyString())).thenReturn(token);

        // Act
        ResponseEntity<AuthResponse> response = studentController.login(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new AuthResponse(token), response.getBody());
        verify(studentService, times(1)).authenticate(any(StudentDto.class));
        verify(jwtUtil, times(1)).generateToken(anyString());
    }

    // Test for enrollToCourse method
    @Test
    public void testEnrollToCourse_WhenValidCourseAndStudent_ShouldEnrollStudent() {
        // Arrange
        Long courseId = 1L;
        Long studentId = 1L;

        // Act
        ResponseEntity<String> response = studentController.enrollToCourse(courseId, studentId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Enrolled to course successfully", response.getBody());
        verify(studentService, times(1)).enrollStudentToCourse(courseId, studentId);
    }

    // Test for withdrawToCourse method
    @Test
    public void testWithdrawToCourse_WhenValidCourseAndStudent_ShouldWithdrawStudent() {
        // Arrange
        Long courseId = 1L;
        Long studentId = 1L;

        // Act
        ResponseEntity<String> response = studentController.withdrawToCourse(courseId, studentId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Withdrawn from course successfully", response.getBody());
        verify(studentService, times(1)).withdrawStudentToCourse(courseId, studentId);
    }

    // Test for getStudentCourses method
    @Test
    public void testGetStudentCourses_WhenValidStudentId_ShouldReturnCourses() {
        // Arrange
        Long studentId = 1L;
        List<CourseDto> courses = Arrays.asList(new CourseDto(), new CourseDto());
        when(studentService.getStudentCourses(studentId)).thenReturn(courses);

        // Act
        ResponseEntity<List<CourseDto>> response = studentController.getStudentCourses(studentId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(courses, response.getBody());
        verify(studentService, times(1)).getStudentCourses(studentId);
    }
}
