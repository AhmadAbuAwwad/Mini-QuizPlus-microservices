package com.example.loginService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "error";

    /**
     * @param ex
     * @param request
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = {AuthenticationException.class})
    protected ResponseEntity<Object> MedicineNotFound(AuthenticationException ex, WebRequest request) throws IOException {
        ex.printStackTrace();
        String details = ex.getMessage();
        ErrorDetails error = new
                ErrorDetails(ResponseErrors.UNAUTHENTICATED.getType(), ResponseErrors.UNAUTHENTICATED.getCode(),
                ((ServletWebRequest) request).getRequest().getRequestURI(), details);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    /**
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = RefreshTokenException.class)
    public ResponseEntity<Object> handleTokenRefreshException(RefreshTokenException ex, WebRequest request) {
        String details = ex.getMessage();
        ErrorDetails error = new
                ErrorDetails(ResponseErrors.UNAUTHENTICATED.getType(), ResponseErrors.UNAUTHENTICATED.getCode(),
                ((ServletWebRequest) request).getRequest().getRequestURI(), details);

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

}

