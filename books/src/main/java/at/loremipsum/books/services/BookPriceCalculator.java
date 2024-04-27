package at.loremipsum.books.services;

import at.loremipsum.books.entities.BookEntity;
import at.loremipsum.books.entities.Language;
import at.loremipsum.books.exceptions.InvalidDataException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class BookPriceCalculator {
    private static final float BASE_COMPENSATION = 100f;
    private static final TreeMap<Integer, Float> COMPENSATION_LADDER = new TreeMap<>(Map.ofEntries(
            Map.entry(0, 0.7f),
            Map.entry(50, 1f),
            Map.entry(100, 1.1f),
            Map.entry(200, 1.2f),
            Map.entry(300, 1.3f),
            Map.entry(500, 1.5f)
    ));
    private static final int AGE_COMPENSATION_THRESHOLD = 1900;
    private static final float GERMAN_COMPENSATION_FACTOR = 1.1f;

    public float getCompensation(BookEntity book) {
        if (book.getPages() == 0 || book.getDatePublished() == null) {
            throw new InvalidDataException("Book is missing data to calculate compensation …");
        }

        float compensation = BASE_COMPENSATION * getCompensationFactor(book.getPages()) + getAgeCompensation(book.getDatePublished().getYear());

        compensation *= getLanguageCompensationFactor(book.getLanguage());

        return compensation;
    }

    public float getCompensationFactor(int pages) {
        return COMPENSATION_LADDER.floorEntry(pages).getValue();
    }

    public float getAgeCompensation(int year) {
        if (year < AGE_COMPENSATION_THRESHOLD) {
            return 15;
        } else {
            return 0;
        }
    }

    public float getBaseCompensation() {
        return BASE_COMPENSATION;
    }

    public float getLanguageCompensationFactor(Language language) {
        if (language == Language.GERMAN) {
            return GERMAN_COMPENSATION_FACTOR;
        } else {
            return 1f;
        }
    }

}
