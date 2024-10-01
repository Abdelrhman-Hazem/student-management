package com.bbyn.studentmanagement.service;

import static org.junit.jupiter.api.Assertions.*;

import com.bbyn.studentmanagement.exception.CourseDoesNotExistException;
import com.bbyn.studentmanagement.exception.DuplicateCourseNameException;
import com.bbyn.studentmanagement.mapper.CourseMapper;
import com.bbyn.studentmanagement.model.dto.CourseDetailsDto;
import com.bbyn.studentmanagement.model.dto.CourseDto;
import com.bbyn.studentmanagement.model.entity.Course;
import com.bbyn.studentmanagement.model.request.CourseCreationRequest;
import com.bbyn.studentmanagement.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseService courseService;

    @Test
    public void testCreateCourse_WhenCourseNameExists_ShouldThrowDuplicateCourseNameException() {
        // Arrange
        CourseCreationRequest request = new CourseCreationRequest();
        request.setName("Existing Course");

        when(courseRepository.existsByName(request.getName())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateCourseNameException.class, () -> {
            courseService.createCourse(request);
        });

        verify(courseRepository, times(1)).existsByName(request.getName());
    }

    @Test
    public void testCreateCourse_WhenCourseNameDoesNotExist_ShouldSaveAndReturnCourseDto() {
        // Arrange
        CourseCreationRequest request = new CourseCreationRequest();
        request.setName("New Course");

        Course course = new Course();
        CourseDto courseDto = new CourseDto();

        when(courseRepository.existsByName(request.getName())).thenReturn(false);
        when(courseMapper.courseCreationRequestToCourse(request)).thenReturn(course);
        when(courseMapper.courseToCourseDto(course)).thenReturn(courseDto);

        // Act
        CourseDto result = courseService.createCourse(request);

        // Assert
        assertEquals(courseDto, result);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testGetAllCourses_WhenCoursesExist_ShouldReturnListOfCourseDtos() {
        // Arrange
        Course course1 = new Course();
        Course course2 = new Course();
        List<Course> courses = Arrays.asList(course1, course2);

        CourseDto courseDto1 = new CourseDto();
        CourseDto courseDto2 = new CourseDto();

        when(courseRepository.findAll()).thenReturn(courses);
        when(courseMapper.courseToCourseDto(course1)).thenReturn(courseDto1);
        when(courseMapper.courseToCourseDto(course2)).thenReturn(courseDto2);

        // Act
        List<CourseDto> result = courseService.getAllCourses();

        // Assert
        assertEquals(2, result.size());
        assertEquals(courseDto1, result.get(0));
        assertEquals(courseDto2, result.get(1));
    }

    @Test
    public void testGetAllCourses_WhenNoCoursesExist_ShouldReturnEmptyList() {
        // Arrange
        when(courseRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<CourseDto> result = courseService.getAllCourses();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetCourseById_WhenCourseExists_ShouldReturnCourseDetailsDto() {
        // Arrange
        Long courseId = 1L;
        Course course = new Course();
        CourseDetailsDto courseDetailsDto = new CourseDetailsDto();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(courseMapper.courseToCourseDetailsDto(course)).thenReturn(courseDetailsDto);

        // Act
        CourseDetailsDto result = courseService.getCourseById(courseId);

        // Assert
        assertEquals(courseDetailsDto, result);
    }

    @Test
    public void testGetCourseById_WhenCourseDoesNotExist_ShouldThrowCourseDoesNotExistException() {
        // Arrange
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CourseDoesNotExistException.class, () -> {
            courseService.getCourseById(courseId);
        });

        verify(courseRepository, times(1)).findById(courseId);
    }
}
