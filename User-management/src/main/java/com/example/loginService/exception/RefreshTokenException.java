package com.example.loginService.exception;

public class RefreshTokenException extends RuntimeException {

    /**
     * @param message
     * @param cause
     */
    public RefreshTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public RefreshTokenException(String message) {
        super(message);
    }

    /* *
     *
     * @param cause
     */
    public RefreshTokenException(Throwable cause) {
        super(cause);
    }
}
