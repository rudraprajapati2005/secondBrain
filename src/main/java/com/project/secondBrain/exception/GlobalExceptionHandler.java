package com.project.secondBrain.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<List<String>> handleValidationException(
        MethodArgumentNotValidException ex) {

    List<String> errors =
            ex.getBindingResult()
              .getFieldErrors()
              .stream()
              .map(fieldError -> fieldError.getDefaultMessage())
              .toList();

    return ResponseEntity.badRequest().body(errors);
}

    @ExceptionHandler(RuntimeException.class)
   public ResponseEntity<String>handleException(RuntimeException ex)
   {
    return ResponseEntity
            .badRequest()
            .body(ex.getMessage());

   }
}   
