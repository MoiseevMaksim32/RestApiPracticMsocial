package com.example.RestApiPracticMsocial.Controller;

import com.example.RestApiPracticMsocial.Model.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHadler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
