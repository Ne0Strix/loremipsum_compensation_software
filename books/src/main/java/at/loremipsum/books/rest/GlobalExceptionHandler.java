package at.loremipsum.books.rest;

import at.loremipsum.books.exceptions.InvalidDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for catching and handling specific exceptions across the entire application to centralize error handling.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Handles exceptions of type InvalidDataException thrown from any part of the application.
     * This method formats the exception so that it can be handled by the client.
     *
     * @param invalidDataException The exception caught by this handler.
     * @return A BAD_REQUEST-response containing the details of the exception.
     */
    @ExceptionHandler({InvalidDataException.class})
    public ResponseEntity<Object> handleInvalidDataException(InvalidDataException invalidDataException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalidDataException.getMessage());
    }
}
