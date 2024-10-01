package com.bbyn.studentmanagement.exception;

public class StudentDoesNotExistException extends RuntimeException {
    public StudentDoesNotExistException(String message) {
        super(message);
    }
}
