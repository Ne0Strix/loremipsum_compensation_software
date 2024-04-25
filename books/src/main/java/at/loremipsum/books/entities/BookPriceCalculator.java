package at.loremipsum.books.entities;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class BookPriceCalculator {
    private static final int BASE_COMPENSATION = 100;
    private static final Map<Integer, Float> COMPENSATION_LADDER = new TreeMap<>(Map.ofEntries(
            Map.entry(0, 0.7f),
            Map.entry(50, 1f),
            Map.entry(100, 1.1f),
            Map.entry(200, 1.2f),
            Map.entry(300, 1.3f),
            Map.entry(500, 1.5f)
    ));
    private static final int AGE_COMPENSATION_THRESHOLD = 1900;

    public void getCompensation(BookEntity book) {
        // todo
    }

}
