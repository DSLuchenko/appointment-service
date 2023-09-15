package ru.dsluchenko.appointment.service.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.dsluchenko.appointment.service.exception.ResourceNotFoundException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleResourceNotFound(MethodArgumentTypeMismatchException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFound(ResourceNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    private class ErrorResponse {
        public final String message;

        public ErrorResponse(String message) {
            this.message = message;
        }
    }
}
