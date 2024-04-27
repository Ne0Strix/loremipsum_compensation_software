package at.loremipsum.books.services;

import at.loremipsum.books.dto.BookDto;
import at.loremipsum.books.entities.Genre;
import at.loremipsum.books.entities.Language;
import at.loremipsum.books.exceptions.InvalidDataException;
import org.springframework.stereotype.Service;


@Service
public class BookService {
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

    public String normalizeIsbn(String isbn) {
        return isbn.replace("-", "").replace(" ", "");
    }
}
