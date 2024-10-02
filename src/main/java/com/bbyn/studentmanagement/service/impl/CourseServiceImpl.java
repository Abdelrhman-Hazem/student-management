package com.bbyn.studentmanagement.service.impl;

import com.bbyn.studentmanagement.exception.CourseDoesNotExistException;
import com.bbyn.studentmanagement.exception.DuplicateCourseNameException;
import com.bbyn.studentmanagement.mapper.CourseMapper;
import com.bbyn.studentmanagement.model.dto.CourseDto;
import com.bbyn.studentmanagement.model.dto.CourseDetailsDto;
import com.bbyn.studentmanagement.model.entity.Course;
import com.bbyn.studentmanagement.model.request.CourseCreationRequest;
import com.bbyn.studentmanagement.repository.CourseRepository;
import com.bbyn.studentmanagement.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public CourseDto createCourse(CourseCreationRequest courseCreationRequest) {
        if (courseRepository.existsByName(courseCreationRequest.getName())) {
            throw new DuplicateCourseNameException("Course name '" + courseCreationRequest.getName() + "' is already reserved.");
        }
        Course course = courseMapper.courseCreationRequestToCourse(courseCreationRequest);
        courseRepository.save(course);

        return courseMapper.courseToCourseDto(course);
    }

    @Override
    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(courseMapper::courseToCourseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CourseDetailsDto getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseDoesNotExistException("No Course Created with Specified Id"));

        return courseMapper.courseToCourseDetailsDto(course);
    }
}

