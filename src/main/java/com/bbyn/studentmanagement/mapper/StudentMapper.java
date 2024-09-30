package com.bbyn.studentmanagement.mapper;

import com.bbyn.studentmanagement.model.dto.StudentDto;
import com.bbyn.studentmanagement.model.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class StudentMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    public abstract StudentDto studentToStudentDto(Student student);

    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    public abstract Student studentDtoToStudent(StudentDto student);

    @Named("encodePassword")
    protected String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}

