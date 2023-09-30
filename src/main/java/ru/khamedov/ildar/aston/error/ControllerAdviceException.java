package ru.khamedov.ildar.aston.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ControllerAdviceException {
    @ExceptionHandler
    public ResponseEntity<JsonError> catchHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new ResponseEntity<>(new JsonError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<JsonError> catchHttpClientErrorException(HttpClientErrorException e) {
        return new ResponseEntity<>(new JsonError(e.getStatusCode().value(), e.getStatusText()), e.getStatusCode());
    }

}
