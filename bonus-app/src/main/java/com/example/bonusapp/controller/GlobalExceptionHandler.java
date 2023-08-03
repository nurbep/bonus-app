package com.example.bonusapp.controller;

import com.example.bonusapp.dto.ErrorResponse;
import com.example.bonusapp.exception.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity <ErrorResponse> customerNotFoundException(CustomerNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(404, ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity < ErrorResponse > (errorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity < ? > globleExcpetionHandler(Exception ex) {

        ErrorResponse errorResponse = new ErrorResponse(500, ex.getMessage(), LocalDateTime.now());

        return new ResponseEntity < > (errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}