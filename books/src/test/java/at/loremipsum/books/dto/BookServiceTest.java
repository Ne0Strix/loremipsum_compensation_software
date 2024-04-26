package at.loremipsum.books.dto;

import at.loremipsum.books.exceptions.InvalidDataException;
import at.loremipsum.books.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BookServiceTest {
    @Autowired
    private BookService validator;

    @ParameterizedTest
    @ValueSource(strings = {"978-3902980816", "978 1473224 469 ", "9798880228621"})
    void testIsbnValid(String isbn) {
        assertTrue(validator.validateIsbn(isbn));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0123", "978+1473224469"})
    void testIsbnInvalidFormat(String isbn) {
        assertFalse(validator.validateIsbn(isbn));
    }


    @ParameterizedTest
    @ValueSource(strings = {"9797880228621", "9798881228621", "9798880228627"})
    void testIsbnInvalidChecksum(String isbn) {
        assertFalse(validator.validateIsbn(isbn));
    }

    @Test
    void testBookDtoValidation_MissingIsbn() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("The Book");
        assertThrows(InvalidDataException.class, () -> {
            validator.validateBooksDto(bookDto);
        });
    }

    @Test
    void testBookDtoValidation_MissingTitle() {
        BookDto bookDto = new BookDto();
        bookDto.setIsbn("9783649646082");
        assertThrows(InvalidDataException.class, () -> {
            validator.validateBooksDto(bookDto);
        });
    }

    @Test
    void testBookDtoValidation_invalidIsbn() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("The Book");
        bookDto.setIsbn("9783649646081");
        assertThrows(InvalidDataException.class, () -> {
            validator.validateBooksDto(bookDto);
        });
    }
}