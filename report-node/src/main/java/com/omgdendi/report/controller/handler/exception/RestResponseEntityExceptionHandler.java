package com.omgdendi.report.controller.handler.exception;


import com.omgdendi.report.exception.CategoryAlreadyExistException;
import com.omgdendi.report.exception.CategoryNotFoundException;
import com.omgdendi.report.exception.EssayNotFoundException;
import com.omgdendi.report.exception.UserAlreadyExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {
            Exception.class
    })
    public ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest request) {
        return ResponseEntity.internalServerError().body("Произошла ошибка");
    }

    @ExceptionHandler(value = {
            CategoryNotFoundException.class,
            EssayNotFoundException.class
    })
    public ResponseEntity<?> handlerNotFoundException(Exception ex, WebRequest request) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(value = {
            CategoryAlreadyExistException.class,
            UserAlreadyExistException.class
    })
    public ResponseEntity<?> handlerAlreadyExistException(Exception ex, WebRequest request) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}
