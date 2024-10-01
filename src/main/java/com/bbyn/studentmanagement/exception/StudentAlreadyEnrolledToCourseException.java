package com.bbyn.studentmanagement.exception;

public class StudentAlreadyEnrolledToCourseException extends RuntimeException {
    public StudentAlreadyEnrolledToCourseException(String message) {
        super(message);
    }
}
