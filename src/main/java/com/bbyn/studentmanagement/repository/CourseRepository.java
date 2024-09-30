package com.bbyn.studentmanagement.repository;

import com.bbyn.studentmanagement.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByName(String name);
}

