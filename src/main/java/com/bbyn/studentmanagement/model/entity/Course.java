package com.bbyn.studentmanagement.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;
    private int capacity;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;
}

