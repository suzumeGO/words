package com.example.wordstraining.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(WordAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> exceptionWordAlreadyExistsHandler() {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage("Слово уже существует. ");
        return ResponseEntity
                .internalServerError()
                .body(errorDetails);
    }

}
