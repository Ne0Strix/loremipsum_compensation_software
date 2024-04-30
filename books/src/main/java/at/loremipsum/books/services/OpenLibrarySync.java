package at.loremipsum.books.services;

import at.loremipsum.books.entities.BookEntity;
import at.loremipsum.books.entities.BooksRepository;
import at.loremipsum.books.entities.Language;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service for fetching data from openlibrary.org and updating local book metadata.
 * Each book is updated exactly once.
 */

@Service
public class OpenLibrarySync {
    private static final String REQUEST_URL = "https://openlibrary.org/isbn/";

    private final BooksRepository booksRepository;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Autowired
    public OpenLibrarySync(RestTemplate restTemplate, BooksRepository booksRepository, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.booksRepository = booksRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Periodically fetches and updates books that are marked as not enriched in the repository.
     * Runs every 5 seconds and updates the database in batches of 10.
     */
    @Scheduled(fixedRate = 5000)
    public void updateBooks() {
        int page = 0;
        int pageSize = 10;

        Pageable pageable = PageRequest.of(page, pageSize);
        Slice<BookEntity> toUpdate = booksRepository.findFirst10BookEntityByIsEnrichedIsFalse(pageable);
        toUpdate.forEach(this::updateBook);
    }

    /**
     * Retrieves the JSON metadata for a book from the Open Library API using the book's ISBN.
     *
     * @param book The book for which metadata is being fetched.
     * @return The JSON node containing the book metadata.
     * @throws RuntimeException if there is any error in fetching or parsing the JSON data.
     */
    private JsonNode getJson(BookEntity book) {
        try {
            String url = REQUEST_URL + book.getIsbn() + ".json";
            String json = restTemplate.getForObject(url, String.class);
            return objectMapper.readTree(json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the metadata of a single BookEntity based on the data retrieved from the Open Library API.
     * This method enriches the book with publisher information, page count, and language if available.
     *
     * @param book The book entity to update.
     */
    public void updateBook(BookEntity book) {
        JsonNode node = getJson(book);
        boolean isEnriched = false;
        if (node.has("publishers") && node.get("publishers").isArray()) {
            StringBuilder publishers = new StringBuilder();
            for (JsonNode publisher : node.get("publishers")) {
                if (publishers.length() > 0) {
                    publishers.append(", ");
                }
                publishers.append(publisher.asText());
            }
            book.setPublisher(publishers.toString());
            isEnriched = true;
        }

        if (node.has("number_of_pages") && node.get("number_of_pages").isNumber()) {
            book.setPages(node.get("number_of_pages").asInt());
            isEnriched = true;
        }

        if (node.has("languages") && node.get("languages").isArray()) {
            String languageKey = node.get("languages").get(0).get("key").asText();
            String languageCode = languageKey.substring(languageKey.lastIndexOf('/') + 1);
            book.setLanguage(Language.fromCode(languageCode));
            isEnriched = true;
        }
        book.setEnriched(isEnriched);
        booksRepository.save(book);
    }
}
