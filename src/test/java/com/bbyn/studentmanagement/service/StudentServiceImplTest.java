package com.bbyn.studentmanagement.service;

import static org.junit.jupiter.api.Assertions.*;

import com.bbyn.studentmanagement.exception.*;
import com.bbyn.studentmanagement.mapper.CourseMapper;
import com.bbyn.studentmanagement.mapper.StudentMapper;
import com.bbyn.studentmanagement.model.dto.CourseDto;
import com.bbyn.studentmanagement.model.dto.StudentDto;
import com.bbyn.studentmanagement.model.entity.Course;
import com.bbyn.studentmanagement.model.entity.Student;
import com.bbyn.studentmanagement.repository.CourseRepository;
import com.bbyn.studentmanagement.repository.StudentRepository;
import com.bbyn.studentmanagement.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private StudentServiceImpl studentService;


    // Test for registerStudent method
    @Test
    public void testRegisterStudent_WhenUsernameExists_ShouldThrowDuplicateUsernameException() {
        // Arrange
        StudentDto studentDto = new StudentDto();
        studentDto.setUsername("existingUser");

        when(studentRepository.existsByUsername(studentDto.getUsername())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateUsernameException.class, () -> {
            studentService.registerStudent(studentDto);
        });

        verify(studentRepository, times(1)).existsByUsername(studentDto.getUsername());
    }

    @Test
    public void testRegisterStudent_WhenUsernameDoesNotExist_ShouldSaveAndReturnStudentDto() {
        // Arrange
        StudentDto studentDto = new StudentDto();
        studentDto.setUsername("newUser");

        Student student = new Student();

        when(studentRepository.existsByUsername(studentDto.getUsername())).thenReturn(false);
        when(studentMapper.studentDtoToStudent(studentDto)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);
        when(studentMapper.studentToStudentDto(student)).thenReturn(studentDto);

        // Act
        StudentDto result = studentService.registerStudent(studentDto);

        // Assert
        assertEquals(studentDto, result);
        verify(studentRepository, times(1)).save(student);
    }

    // Test for authenticate method
    @Test
    public void testAuthenticate_WhenCredentialsAreValid_ShouldReturnStudentDto() {
        // Arrange
        StudentDto studentDto = new StudentDto();
        studentDto.setUsername("user");
        studentDto.setPassword("password");

        Student student = new Student();
        student.setPassword("encodedPassword");

        when(studentRepository.findByUsername(studentDto.getUsername())).thenReturn(student);
        when(passwordEncoder.matches(studentDto.getPassword(), student.getPassword())).thenReturn(true);
        when(studentMapper.studentToStudentDto(student)).thenReturn(studentDto);

        // Act
        StudentDto result = studentService.authenticate(studentDto);

        // Assert
        assertEquals(studentDto, result);
    }

    @Test
    public void testAuthenticate_WhenCredentialsAreInvalid_ShouldThrowStudentCredentialsInvalidException() {
        // Arrange
        StudentDto studentDto = new StudentDto();
        studentDto.setUsername("user");
        studentDto.setPassword("wrongPassword");

        Student student = new Student();
        student.setPassword("encodedPassword");

        when(studentRepository.findByUsername(studentDto.getUsername())).thenReturn(student);
        when(passwordEncoder.matches(studentDto.getPassword(), student.getPassword())).thenReturn(false);

        // Act & Assert
        assertThrows(StudentCredentialsInvalidException.class, () -> {
            studentService.authenticate(studentDto);
        });
    }

    // Test for enrollStudentToCourse method
    @Test
    public void testEnrollStudentToCourse_WhenStudentAndCourseExist_ShouldEnrollStudentToCourse() {
        // Arrange
        Long studentId = 1L;
        Long courseId = 1L;

        Student student = Student.builder()
                .id(studentId)
                .courses(new ArrayList<>())
                .build();
        Course course = Course.builder()
                .capacity(10)
                .students(new ArrayList<>())
                .build();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(courseRepository.save(course)).thenReturn(course);

        // Act
        studentService.enrollStudentToCourse(courseId, studentId);

        // Assert
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testEnrollStudentToCourse_WhenStudentAlreadyEnrolled_ShouldThrowStudentAlreadyEnrolledToCourseException() {
        // Arrange
        Long studentId = 1L;
        Long courseId = 1L;

        Student student = Student.builder()
                .id(studentId)
                .build();
        Course course = Course.builder()
                .id(courseId)
                .capacity(3)
                .students(Arrays.asList(student))
                .build();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Act & Assert
        assertThrows(StudentAlreadyEnrolledToCourseException.class, () -> {
            studentService.enrollStudentToCourse(courseId, studentId);
        });
    }

    @Test
    public void testEnrollStudentToCourse_WhenCourseIsAtCapacity_ShouldThrowCourseAtCapacityException() {
        // Arrange
        Long studentId = 1L;
        Long courseId = 1L;

        Student student = new Student();
        Course course = new Course();
        course.setCapacity(1);
        course.setStudents(Arrays.asList(new Student()));

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Act & Assert
        assertThrows(CourseAtCapacityException.class, () -> {
            studentService.enrollStudentToCourse(courseId, studentId);
        });
    }

    // Test for withdrawStudentToCourse method
    @Test
    public void testWithdrawStudentToCourse_WhenStudentEnrolled_ShouldRemoveStudentFromCourse() {
        // Arrange
        Long studentId = 1L;
        Long courseId = 1L;

        Student student = Student.builder()
                .id(studentId)
                .build();
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);
        Course course = Course.builder()
                .id(courseId)
                .students(studentList)
                .build();
        List<Course> courseList = new ArrayList<>();
        courseList.add(course);
        student.setCourses(courseList);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Act
        studentService.withdrawStudentToCourse(courseId, studentId);

        // Assert
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testWithdrawStudentToCourse_WhenStudentNotEnrolled_ShouldThrowStudentNotEnrolledToCourseException() {
        // Arrange
        Long studentId = 1L;
        Long courseId = 1L;

        Student student = new Student();
        Course course = new Course();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Act & Assert
        assertThrows(StudentNotEnrolledToCourseException.class, () -> {
            studentService.withdrawStudentToCourse(courseId, studentId);
        });
    }

    // Test for getStudentCourses method
    @Test
    public void testGetStudentCourses_WhenStudentExists_ShouldReturnListOfCourseDtos() {
        // Arrange
        Long studentId = 1L;
        Student student = new Student();
        Course course1 = new Course();
        Course course2 = new Course();
        student.setCourses(Arrays.asList(course1, course2));

        CourseDto courseDto1 = new CourseDto();
        CourseDto courseDto2 = new CourseDto();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseMapper.courseToCourseDto(course1)).thenReturn(courseDto1);
        when(courseMapper.courseToCourseDto(course2)).thenReturn(courseDto2);

        // Act
        List<CourseDto> result = studentService.getStudentCourses(studentId);

        // Assert
        assertEquals(2, result.size());
        assertEquals(courseDto1, result.get(0));
        assertEquals(courseDto2, result.get(1));
    }

    @Test
    public void testGetStudentCourses_WhenStudentDoesNotExist_ShouldThrowStudentDoesNotExistException() {
        // Arrange
        Long studentId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(StudentDoesNotExistException.class, () -> {
            studentService.getStudentCourses(studentId);
        });
    }
}
