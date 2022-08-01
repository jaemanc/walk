package com.org.walk.util;

import io.jsonwebtoken.ExpiredJwtException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Exceptionhandler {

    private final Logger log_error = LogManager.getLogger("com.error");

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> ExpiredJwtExceptionHandler (ExpiredJwtException ex) {
        //System.out.println(" exception catcher!!! ");

        log_error.error(" EXPIRED JWT REQUESTED... " );

        return new ResponseEntity<String>(" EXPIRED JWT REQUESTED...  " , HttpStatus.UNAUTHORIZED);
    }

}
