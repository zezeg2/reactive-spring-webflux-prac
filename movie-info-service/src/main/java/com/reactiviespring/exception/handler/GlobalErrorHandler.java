package com.reactiviespring.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.stream.Collectors;

/**
 * Created by jonghyeon on 2023/03/23,
 * Package : exception.handler
 */
@ControllerAdvice
@Slf4j
public class GlobalErrorHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<String> handleRequestBodyError(WebExchangeBindException exception){
        log.error("Exception Caught in handleRequestBodyError : {}", exception.getMessage(), exception);
        var error = exception.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .sorted().collect(Collectors.joining(","));

        log.error("Error is : {}", error);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
