package com.bbyn.studentmanagement.mapper;

import com.bbyn.studentmanagement.model.dto.CourseDetailsDto;
import com.bbyn.studentmanagement.model.dto.CourseDto;
import com.bbyn.studentmanagement.model.entity.Course;
import com.bbyn.studentmanagement.model.request.CourseCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {StudentMapper.class, CourseSessionMapper.class})
public interface CourseMapper {

    CourseDto courseToCourseDto(Course course);

    Course courseCreationRequestToCourse(CourseCreationRequest courseCreationRequest);

    CourseDetailsDto courseToCourseDetailsDto(Course course);
}

