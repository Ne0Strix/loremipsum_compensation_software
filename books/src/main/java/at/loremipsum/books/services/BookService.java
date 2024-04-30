package at.loremipsum.books.services;

import at.loremipsum.books.dto.BookDto;
import at.loremipsum.books.exceptions.InvalidDataException;
import org.springframework.stereotype.Service;


/**
 * Service class for book-related operations like validation and normalization.
 * This service ensures book data is in a consistent format before processing or storage.
 */
@Service
public class BookService {
    /**
     * Validates that all required fields are present and valid in the DTO before further processing.
     *
     * @param bookDto The BookDto to validate.
     * @throws InvalidDataException if missing or invalid data is found.
     */
    public void validateBooksDto(BookDto bookDto) {
        if (bookDto.getTitle() == null) {
            throw new InvalidDataException("Missing title");
        }

        if (bookDto.getIsbn() == null) {
            throw new InvalidDataException("Missing ISBN");
        }

        if (!validateIsbn(bookDto.getIsbn())) {
            throw new InvalidDataException(bookDto.getIsbn() + " is not a valid ISBN.");
        }
    }

    /**
     * Validates the format of an ISBN and its checksum. Only ISBN-13 values are accepted.
     *
     * @param isbn The ISBN string to validate.
     * @return true if the ISBN passes all checks, false otherwise.
     */
    public boolean validateIsbn(String isbn) {
        String regex = "^\\d{13}$";
        isbn = isbn.replace("-", "").replace(" ", "");

        if (!isbn.matches(regex)) {
            return false;
        }

        int checksum = 0;
        for (int i = 0; i < 12; i++) {
            int value = Character.getNumericValue(isbn.charAt(i));
            checksum = i % 2 == 0 ? checksum + value : checksum + 3 * value;
        }

        checksum = Character.getNumericValue(isbn.charAt(12)) + checksum;

        return checksum % 10 == 0;
    }

    /**
     * Normalizes an ISBN by stripping it of any hyphens and spaces to ensure a consistent format.
     *
     * @param isbn The ISBN string to normalize.
     * @return A string representing the normalized ISBN.
     */
    public String normalizeIsbn(String isbn) {
        return isbn.replace("-", "").replace(" ", "");
    }
}
