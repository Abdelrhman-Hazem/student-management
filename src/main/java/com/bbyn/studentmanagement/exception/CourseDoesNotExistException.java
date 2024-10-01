package com.bbyn.studentmanagement.exception;

public class CourseDoesNotExistException extends RuntimeException {
    public CourseDoesNotExistException(String message) {
        super(message);
    }
}
