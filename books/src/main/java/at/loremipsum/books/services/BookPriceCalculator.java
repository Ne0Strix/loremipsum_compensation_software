package at.loremipsum.books.services;

import at.loremipsum.books.entities.BookEntity;
import at.loremipsum.books.entities.Language;
import at.loremipsum.books.exceptions.InvalidDataException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class BookPriceCalculator {
    /**
     * Base-compensation in EUR for a book.
     */
    private static final float BASE_COMPENSATION = 100f;

    /**
     * Compensation-factor for the book's length, where the key is the lower boundary of the interval the factor applies to.
     */
    private static final TreeMap<Integer, Float> COMPENSATION_LADDER = new TreeMap<>(Map.ofEntries(
            Map.entry(0, 0.7f),
            Map.entry(50, 1f),
            Map.entry(100, 1.1f),
            Map.entry(200, 1.2f),
            Map.entry(300, 1.3f),
            Map.entry(500, 1.5f)
    ));

    /**
     * The year threshold for applying age-based compensation.
     */
    private static final int AGE_COMPENSATION_THRESHOLD = 1900;

    /**
     * Compensation factor for german books.
     */
    private static final float GERMAN_COMPENSATION_FACTOR = 1.1f;

    /**
     * Calculates the total compensation for a book based on its page count, publication year, and language.
     * Throws InvalidDataException if essential data is missing.
     *
     * @param book The BookEntity for which to calculate the compensation.
     * @return The total compensation amount in EUR.
     */
    public float getCompensation(BookEntity book) {
        if (book.getPages() == 0 || book.getDatePublished() == null) {
            throw new InvalidDataException("Book is missing data to calculate compensation …");
        }

        float compensation = BASE_COMPENSATION * getCompensationFactor(book.getPages()) + getAgeCompensation(book.getDatePublished().getYear());

        compensation *= getLanguageCompensationFactor(book.getLanguage());

        return compensation;
    }

    /**
     * Retrieves the compensation factor based on the number of pages in a book.
     *
     * @param pages The number of pages in the book.
     * @return The compensation factor corresponding to the given page count.
     */
    public float getCompensationFactor(int pages) {
        return COMPENSATION_LADDER.floorEntry(pages).getValue();
    }

    /**
     * Determines the additional age compensation based on the publication year of the book.
     *
     * @param year The publication year of the book.
     * @return The age compensation amount.
     */
    public float getAgeCompensation(int year) {
        if (year < AGE_COMPENSATION_THRESHOLD) {
            return 15;
        } else {
            return 0;
        }
    }

    /**
     * Returns the base compensation rate for all books.
     *
     * @return The base compensation amount in EUR.
     */
    public float getBaseCompensation() {
        return BASE_COMPENSATION;
    }

    /**
     * Gets the compensation factor based on the language of the book.
     *
     * @param language The language of the book.
     * @return The language compensation factor.
     */
    public float getLanguageCompensationFactor(Language language) {
        if (language == Language.GERMAN) {
            return GERMAN_COMPENSATION_FACTOR;
        } else {
            return 1f;
        }
    }

}
