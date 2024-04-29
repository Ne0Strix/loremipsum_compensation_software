package at.loremipsum.books.entities;

import at.loremipsum.books.exceptions.InvalidDataException;
import at.loremipsum.books.services.BookPriceCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BookPriceCalculatorTests {

    @Autowired
    private BookPriceCalculator calculator;

    private static Stream<Arguments> providePagesAndCompensationFactors() {
        return Stream.of(
                Arguments.of(0, 0.7f),
                Arguments.of(10, 0.7f),
                Arguments.of(50, 1.0f),
                Arguments.of(75, 1.0f),
                Arguments.of(100, 1.1f),
                Arguments.of(150, 1.1f),
                Arguments.of(200, 1.2f),
                Arguments.of(250, 1.2f),
                Arguments.of(300, 1.3f),
                Arguments.of(400, 1.3f),
                Arguments.of(500, 1.5f),
                Arguments.of(600, 1.5f)
        );
    }

    @ParameterizedTest
    @MethodSource("providePagesAndCompensationFactors")
    void testGetCompensationFactor(int pages, float expectedFactor) {
        float actualFactor = calculator.getCompensationFactor(pages);
        assertEquals(expectedFactor, actualFactor, 0.001);
    }

    @ParameterizedTest
    @ValueSource(ints = {1899, 1800, 1700})
    void testGetAgeCompensation_LessThan1900(int year) {
        float compensation = calculator.getAgeCompensation(year);
        assertEquals(15, compensation, "Compensation should be 15 for years less than 1900");
    }

    @ParameterizedTest
    @ValueSource(ints = {1900, 1950, 2000, 2020})
    void testGetAgeCompensation_1900OrGreater(int year) {
        float compensation = calculator.getAgeCompensation(year);
        assertEquals(0, compensation, "Compensation should be 0 for years 1900 or greater");
    }

    @Test
    void testCompensationGermanLanguage() {
        BookEntity book = new BookEntity();
        book.setDatePublished(LocalDate.of(1990, 1, 1));
        book.setLanguage(Language.GERMAN);
        book.setPages(148);
        float compensation = calculator.getCompensation(book);
        assertEquals(121, compensation, 0.001);
    }

    @Test
    void testCompensationCalculation() {
        BookEntity book = new BookEntity();
        book.setDatePublished(LocalDate.of(1990, Month.JANUARY, 1));
        book.setPages(148);
        float compensation = calculator.getCompensation(book);
        assertEquals(110, compensation, 0.001);
    }

    @Test
    void testCompensationCalculation_missingPages() {
        BookEntity book = new BookEntity();
        book.setDatePublished(LocalDate.of(2010, Month.JANUARY, 1));
        assertThrows(InvalidDataException.class, () -> {
            calculator.getCompensation(book);
        });
    }

    @Test
    void testCompensationCalculation_missingDate() {
        BookEntity book = new BookEntity();
        book.setPages(100);
        assertThrows(InvalidDataException.class, () -> {
            calculator.getCompensation(book);
        });
    }
}