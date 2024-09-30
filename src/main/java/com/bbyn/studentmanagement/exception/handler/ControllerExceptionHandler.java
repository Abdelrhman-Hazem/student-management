package com.bbyn.studentmanagement.exception.handler;

import com.bbyn.studentmanagement.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(CourseAtCapacityException.class)
    public ResponseEntity<String> handleCourseAtCapacityException(CourseAtCapacityException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(CourseDoesNotExistException.class)
    public ResponseEntity<String> handleCourseDoesNotExistException(CourseDoesNotExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<String> handleDuplicateUsernameException(DuplicateUsernameException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateCourseNameException.class)
    public ResponseEntity<String> handleDuplicateCourseNameException(DuplicateCourseNameException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(StudentCredentialsInvalidException.class)
    public ResponseEntity<String> handleStudentCredentialsInvalidException(StudentCredentialsInvalidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(StudentDoesNotExistException.class)
    public ResponseEntity<String> handleStudentDoesNotExistException(StudentDoesNotExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
