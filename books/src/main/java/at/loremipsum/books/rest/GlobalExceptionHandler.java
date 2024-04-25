package at.loremipsum.books.rest;

import at.loremipsum.books.dto.IsbnException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({IsbnException.class})
    public ResponseEntity<Object> handleIsbnNotFoundException(IsbnException isbnException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(isbnException.getMessage());
    }
}
