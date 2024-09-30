package com.bbyn.studentmanagement.exception;

public class CourseAtCapacityException extends RuntimeException {
    public CourseAtCapacityException(String message) {
        super(message);
    }
}
