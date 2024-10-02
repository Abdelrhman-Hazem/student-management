package com.bbyn.studentmanagement.controller;

import com.bbyn.studentmanagement.model.dto.CourseDetailsDto;
import com.bbyn.studentmanagement.model.dto.CourseDto;
import com.bbyn.studentmanagement.model.request.CourseCreationRequest;
import com.bbyn.studentmanagement.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @Test
    public void testCreateCourse_WhenValidRequest_ShouldReturnCreatedCourse() {
        // Arrange
        CourseCreationRequest request = new CourseCreationRequest();
        CourseDto courseDto = new CourseDto();
        when(courseService.createCourse(request)).thenReturn(courseDto);

        // Act
        ResponseEntity<CourseDto> response = courseController.createCourse(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(courseDto, response.getBody());
        verify(courseService, times(1)).createCourse(request);
    }

    @Test
    public void testGetAllCourses_WhenCalled_ShouldReturnListOfCourses() {
        // Arrange
        List<CourseDto> courses = Arrays.asList(new CourseDto(), new CourseDto());
        when(courseService.getAllCourses()).thenReturn(courses);

        // Act
        ResponseEntity<List<CourseDto>> response = courseController.getAllCourses();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(courses, response.getBody());
        verify(courseService, times(1)).getAllCourses();
    }

    @Test
    public void testGetCourseById_WhenCourseExists_ShouldReturnCourseDetails() {
        // Arrange
        Long courseId = 1L;
        CourseDetailsDto courseDetailsDto = new CourseDetailsDto();
        when(courseService.getCourseById(courseId)).thenReturn(courseDetailsDto);

        // Act
        ResponseEntity<CourseDetailsDto> response = courseController.getCourseById(courseId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(courseDetailsDto, response.getBody());
        verify(courseService, times(1)).getCourseById(courseId);
    }

    @Test
    public void testGetCoursePdfById_WhenCourseExists_ShouldReturnPdfBytes() {
        // Arrange
        Long courseId = 1L;
        CourseDetailsDto courseDetailsDto = CourseDetailsDto.builder()
                .courseSessions(Collections.emptyList())
                .students(Collections.emptyList())
                .build();

        when(courseService.getCourseById(courseId)).thenReturn(courseDetailsDto);

        // Act
        ResponseEntity<byte[]> response = courseController.getCoursePdfById(courseId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_PDF, response.getHeaders().getContentType());
        verify(courseService, times(1)).getCourseById(courseId);
    }
}
