package com.kidneystone.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex) {
        String message = ex.getMessage();

        if (message != null && message.contains("already")) {
            return buildResponse(HttpStatus.CONFLICT, message);
        }

        if (message != null && message.contains("Invalid email or password")) {
            return buildResponse(HttpStatus.UNAUTHORIZED, message);
        }

        if (message != null && message.contains("not found")) {
            return buildResponse(HttpStatus.NOT_FOUND, message);
        }

        if (message != null && message.contains("not allowed")) {
            return buildResponse(HttpStatus.FORBIDDEN, message);
        }

        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Validation error");

        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiError> handleExpiredJwtException(ExpiredJwtException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Token expired. Please login again.");
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleJwtException(JwtException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Invalid token.");
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiError> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        return buildResponse(HttpStatus.FORBIDDEN, "Access denied.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error.");
    }

    private ResponseEntity<ApiError> buildResponse(HttpStatus status, String message) {
        ApiError apiError = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                message
        );

        return ResponseEntity.status(status).body(apiError);
    }
}