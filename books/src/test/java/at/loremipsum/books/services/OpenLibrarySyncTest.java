package at.loremipsum.books.services;

import at.loremipsum.books.entities.BookEntity;
import at.loremipsum.books.entities.BooksRepository;
import at.loremipsum.books.entities.Language;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

public class OpenLibrarySyncTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private BooksRepository booksRepository;
    @Mock
    private RestTemplate restTemplate;
    private OpenLibrarySync openLibrarySync;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateBook() throws JsonProcessingException {
        String data = """
                {
                  "description": {
                    "type": "/type/text",
                    "value": "When Elizabeth Bennet first meets eligible bachelor Fitzwilliam Darcy, she thinks him arrogant and conceited, while he struggles to remain indifferent to her good looks and lively mind. When she later discovers that Darcy has involved himself in the troubled relationship between his friend Bingley and her beloved sister Jane, she is determined to dislike him more than ever. In the sparkling comedy of manners that follows, Jane Austen shows the folly of judging by first impressions and superbly evokes the friendships, gossip and snobberies of provincial middle-class life.\\r\\n--back cover"
                  },
                  "identifiers": {
                    "goodreads": [
                      "60481026"
                    ]
                  },
                  "title": "Pride and Prejudice",
                  "publish_date": "2003",
                  "publishers": [
                    "Penguin Books"
                  ],
                  "type": {
                    "key": "/type/edition"
                  },
                  "isbn_10": [
                    "0141439513"
                  ],
                  "oclc_numbers": [
                    "703617314"
                  ],
                  "isbn_13": [
                    "9780141439518"
                  ],
                  "ocaid": "prideprejudice0000aust_v9a3",
                  "physical_format": "Paperback",
                  "publish_places": [
                    "London"
                  ],
                  "languages": [
                    {
                      "key": "/languages/eng"
                    }
                  ],
                  "copyright_date": "2003",
                  "edition_name": "Tanner introduction (3)",
                  "pagination": "xlii, 435 p.",
                  "covers": [
                    12645114
                  ],
                  "key": "/books/OL37076991M",
                  "number_of_pages": 435,
                  "works": [
                    {
                      "key": "/works/OL66554W"
                    }
                  ],
                  "source_records": [
                    "idb:9780141439518"
                  ],
                  "latest_revision": 5,
                  "revision": 5,
                  "created": {
                    "type": "/type/datetime",
                    "value": "2022-02-23T13:24:48.596516"
                  },
                  "last_modified": {
                    "type": "/type/datetime",
                    "value": "2023-12-19T20:58:24.369843"
                  }
                }
                """;
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(data);

        openLibrarySync = new OpenLibrarySync(restTemplate, booksRepository, objectMapper);

        BookEntity testBook = new BookEntity();
        testBook.setPublisher("Penguin Books");
        testBook.setPages(435);
        testBook.setLanguage(Language.ENGLISH);

        openLibrarySync.updateBook(new BookEntity());

        verify(booksRepository).save(argThat(x -> {
                    return (x.getPublisher().equals(testBook.getPublisher())) &&
                            (x.getLanguage().equals(testBook.getLanguage())) &&
                            (x.getPages() == testBook.getPages()) &&
                            x.isEnriched();
                }
        ));
    }

    @Test
    public void testUpdateBook_noUpdate() {
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("");
        openLibrarySync = new OpenLibrarySync(restTemplate, booksRepository, objectMapper);
        openLibrarySync.updateBook(new BookEntity());
        verify(booksRepository).save(argThat(x -> {
            return !x.isEnriched();
        }));
    }

    @Test
    public void testUpdateBook_partialUpdate() {
        String data = """
                {
                  "description": {
                    "type": "/type/text",
                    "value": "When Elizabeth Bennet first meets eligible bachelor Fitzwilliam Darcy, she thinks him arrogant and conceited, while he struggles to remain indifferent to her good looks and lively mind. When she later discovers that Darcy has involved himself in the troubled relationship between his friend Bingley and her beloved sister Jane, she is determined to dislike him more than ever. In the sparkling comedy of manners that follows, Jane Austen shows the folly of judging by first impressions and superbly evokes the friendships, gossip and snobberies of provincial middle-class life.\\r\\n--back cover"
                  },
                  "identifiers": {
                    "goodreads": [
                      "60481026"
                    ]
                  },
                  "title": "Pride and Prejudice",
                  "publish_date": "2003",
                  "type": {
                    "key": "/type/edition"
                  },
                  "isbn_10": [
                    "0141439513"
                  ],
                  "oclc_numbers": [
                    "703617314"
                  ],
                  "isbn_13": [
                    "9780141439518"
                  ],
                  "ocaid": "prideprejudice0000aust_v9a3",
                  "physical_format": "Paperback",
                  "publish_places": [
                    "London"
                  ],
                  "languages": [
                    {
                      "key": "/languages/eng"
                    }
                  ],
                  "copyright_date": "2003",
                  "edition_name": "Tanner introduction (3)",
                  "pagination": "xlii, 435 p.",
                  "covers": [
                    12645114
                  ],
                  "key": "/books/OL37076991M",
                  "number_of_pages": 435,
                  "works": [
                    {
                      "key": "/works/OL66554W"
                    }
                  ],
                  "source_records": [
                    "idb:9780141439518"
                  ],
                  "latest_revision": 5,
                  "revision": 5,
                  "created": {
                    "type": "/type/datetime",
                    "value": "2022-02-23T13:24:48.596516"
                  },
                  "last_modified": {
                    "type": "/type/datetime",
                    "value": "2023-12-19T20:58:24.369843"
                  }
                }
                """;
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(data);
        openLibrarySync = new OpenLibrarySync(restTemplate, booksRepository, objectMapper);
        openLibrarySync.updateBook(new BookEntity());
        verify(booksRepository).save(argThat(x -> {
            return (x.isEnriched() && x.getPages() == 435) &&
                    (x.getPublisher() == null) &&
                    (x.getLanguage() == null);
        }));
    }
}
