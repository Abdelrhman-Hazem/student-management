package com.bbyn.studentmanagement.service.impl;

import com.bbyn.studentmanagement.exception.*;
import com.bbyn.studentmanagement.mapper.CourseMapper;
import com.bbyn.studentmanagement.mapper.StudentMapper;
import com.bbyn.studentmanagement.model.dto.CourseDto;
import com.bbyn.studentmanagement.model.dto.StudentDto;
import com.bbyn.studentmanagement.model.entity.Course;
import com.bbyn.studentmanagement.model.entity.Student;
import com.bbyn.studentmanagement.repository.CourseRepository;
import com.bbyn.studentmanagement.repository.StudentRepository;
import com.bbyn.studentmanagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public StudentDto registerStudent(StudentDto studentDto) {
        if (studentRepository.existsByUsername(studentDto.getUsername())) {
            throw new DuplicateUsernameException("Username '" + studentDto.getUsername() + "' is already reserved.");
        }

        Student student = studentMapper.studentDtoToStudent(studentDto);
        student = studentRepository.save(student);

        return studentMapper.studentToStudentDto(student);
    }

    @Override
    public StudentDto authenticate(StudentDto studentDto) {
        Student student = studentRepository.findByUsername(studentDto.getUsername());
        if (student != null && passwordEncoder.matches(studentDto.getPassword(), student.getPassword())) {
            return studentMapper.studentToStudentDto(student);
        } else {
            throw new StudentCredentialsInvalidException("No Such Student Registered");
        }
    }

    @Override
    @CacheEvict(value = "studentCourse", key = "#studentId")
    public void enrollStudentToCourse(Long courseId, Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentDoesNotExistException("No Student Registered with Specified Id"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseDoesNotExistException("No Course Created with Specified Id"));

        if (course.getStudents().stream()
                .anyMatch(courseStudent -> courseStudent.getId() == studentId)) {
            throw new StudentAlreadyEnrolledToCourseException("Already Enrolled in course: " + course.getName());
        }

        if (course.getStudents().size() >= course.getCapacity()) {
            throw new CourseAtCapacityException("Course capacity reached");
        }

        course.getStudents().add(student);
        student.getCourses().add(course);
        courseRepository.save(course);
    }

    @Override
    @CacheEvict(value = "studentCourse", key = "#studentId")
    public void withdrawStudentToCourse(Long courseId, Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentDoesNotExistException("No Student Registered with Specified Id"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseDoesNotExistException("No Course Created with Specified Id"));

        if (CollectionUtils.isEmpty(course.getStudents())) {
            throw new StudentNotEnrolledToCourseException("Not Enrolled in course: " + course.getName());
        }

        boolean studentRemoved = course.getStudents().removeIf(
                courseStudent -> courseStudent.getId() == studentId
        );
        boolean courseRemoved = student.getCourses().removeIf(
                studentCourse -> studentCourse.getId() == courseId
        );
        if(!studentRemoved || !courseRemoved){
            throw new StudentNotEnrolledToCourseException("Not Enrolled in course: " + course.getName());
        }

        courseRepository.save(course);
    }

    @Override
    @Cacheable(value = "studentCourse", key = "#studentId", unless = "#result == null")
    public List<CourseDto> getStudentCourses(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentDoesNotExistException("No Student Registered with Specified Id"));

        return student.getCourses().stream()
                .map(courseMapper::courseToCourseDto)
                .collect(Collectors.toList());
    }
}

