package com.example.loginService.exception;
public class AuthenticationException extends RuntimeException {

    /**
     *
     * @param message
     * @param cause
     */
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     *
      * @param message
     */
    public AuthenticationException(String message) {
        super(message);
    }

    /**
     *
     * @param cause
     */
    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}
