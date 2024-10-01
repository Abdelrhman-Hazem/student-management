package com.bbyn.studentmanagement.mapper;


import com.bbyn.studentmanagement.model.dto.CourseSessionDto;
import com.bbyn.studentmanagement.model.entity.CourseSession;
import com.bbyn.studentmanagement.model.request.CourseSessionRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseSessionMapper {
    CourseSessionDto courseSessionToCourseSessionDto (CourseSession courseSession);

    CourseSession courseSessionRequestToCourseSession(CourseSessionRequest courseSessionRequest);
}
