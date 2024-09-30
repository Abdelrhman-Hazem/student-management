package com.bbyn.studentmanagement.mapper;

import com.bbyn.studentmanagement.model.dto.CourseDetailsDto;
import com.bbyn.studentmanagement.model.dto.CourseDto;
import com.bbyn.studentmanagement.model.entity.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = StudentMapper.class)
public interface CourseMapper {

    CourseDto courseToCourseDto(Course course);

    Course courseDtoToCourse(CourseDto courseDto);

    CourseDetailsDto courseToCourseWithStudentsDto(Course course);
}

