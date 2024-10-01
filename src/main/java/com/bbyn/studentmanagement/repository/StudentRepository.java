package com.bbyn.studentmanagement.repository;

import com.bbyn.studentmanagement.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUsername(String username);
    boolean existsByUsername(String username);
}

