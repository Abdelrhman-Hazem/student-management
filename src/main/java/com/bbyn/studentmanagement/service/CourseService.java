package com.bbyn.studentmanagement.service;

import com.bbyn.studentmanagement.exception.CourseDoesNotExistException;
import com.bbyn.studentmanagement.exception.DuplicateCourseNameException;
import com.bbyn.studentmanagement.mapper.CourseMapper;
import com.bbyn.studentmanagement.model.dto.CourseDto;
import com.bbyn.studentmanagement.model.dto.CourseDetailsDto;
import com.bbyn.studentmanagement.model.entity.Course;
import com.bbyn.studentmanagement.model.request.CourseCreationRequest;
import com.bbyn.studentmanagement.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseDto createCourse(CourseCreationRequest courseCreationRequest) {
        if (courseRepository.existsByName(courseCreationRequest.getName())) {
            throw new DuplicateCourseNameException("Course name '" + courseCreationRequest.getName() + "' is already reserved.");
        }
        Course course = courseMapper.courseCreationRequestToCourse(courseCreationRequest);
        courseRepository.save(course);

        return courseMapper.courseToCourseDto(course);
    }

    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(course -> courseMapper.courseToCourseDto(course))
                .collect(Collectors.toList());
    }

    public CourseDetailsDto getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseDoesNotExistException("No Course Created with Specified Id"));

        return courseMapper.courseToCourseDetailsDto(course);
    }
}

