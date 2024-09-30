package com.bbyn.studentmanagement.service;

import com.bbyn.studentmanagement.exception.*;
import com.bbyn.studentmanagement.mapper.CourseMapper;
import com.bbyn.studentmanagement.mapper.StudentMapper;
import com.bbyn.studentmanagement.model.dto.CourseDto;
import com.bbyn.studentmanagement.model.dto.StudentDto;
import com.bbyn.studentmanagement.model.entity.Course;
import com.bbyn.studentmanagement.model.entity.Student;
import com.bbyn.studentmanagement.repository.CourseRepository;
import com.bbyn.studentmanagement.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;
    private final PasswordEncoder passwordEncoder;

    public StudentDto registerStudent(StudentDto studentDto) {
        if (studentRepository.existsByUsername(studentDto.getUsername())) {
            throw new DuplicateUsernameException("Username '" + studentDto.getUsername() + "' is already reserved.");
        }

        Student student = studentMapper.studentDtoToStudent(studentDto);
        student = studentRepository.save(student);

        return studentMapper.studentToStudentDto(student);
    }

    public StudentDto authenticate(StudentDto studentDto) {
        Student student = studentRepository.findByUsername(studentDto.getUsername());
        if (student != null && passwordEncoder.matches(studentDto.getPassword(), student.getPassword())) {
            return studentMapper.studentToStudentDto(student);
        }else {
            throw new StudentCredentialsInvalidException("No Such Student Registered");
        }
    }

    public void enrollStudentToCourse(Long courseId, Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentDoesNotExistException("No Student Registered with Specified Id"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseDoesNotExistException("No Course Created with Specified Id"));

        if (course.getStudents().size() >= course.getCapacity()) {
            throw new CourseAtCapacityException("Course capacity reached");
        }

        course.getStudents().add(student);
        courseRepository.save(course);
    }

    public List<CourseDto> getStudentCourses(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentDoesNotExistException("No Student Registered with Specified Id"));

        return student.getCourses().stream()
                .map(course -> courseMapper.courseToCourseDto(course))
                .collect(Collectors.toList());
    }
}

