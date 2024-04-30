package at.loremipsum.books.exceptions;

/**
 * Custom exception to be thrown when data validation fails or handling invalid data.
 */
public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }
}
