package com.example.shop.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
     public Map<String, String> handleEmployeeNotFound(
            EmployeeNotFoundException exception
     ) {
        return Map.of("error", exception.getMessage());
     }

     @ExceptionHandler(MethodArgumentNotValidException.class)
     @ResponseStatus(HttpStatus.BAD_REQUEST)
     public Map<String, String> handleValidation(
         MethodArgumentNotValidException exception
     ) {
         Map<String, String> errors = new HashMap<>();

         exception.getBindingResult()
                  .getFieldErrors()
                  .forEach( error ->
                        errors.put(
                           error.getField(),error.getDefaultMessage()
                        )
                  );

         return errors;
     }
}