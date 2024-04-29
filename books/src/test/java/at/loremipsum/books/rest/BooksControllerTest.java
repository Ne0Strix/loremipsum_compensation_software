package at.loremipsum.books.rest;

import at.loremipsum.books.BooksApplication;
import at.loremipsum.books.dto.BookDto;
import at.loremipsum.books.entities.BookEntity;
import at.loremipsum.books.entities.BooksRepository;
import at.loremipsum.books.entities.Genre;
import at.loremipsum.books.entities.Language;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = BooksApplication.class)
@AutoConfigureMockMvc

public class BooksControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private ObjectMapper mapper;

    // ************ //
    // GET /books   //
    // ************ //

    @Test
    public void testValidListRequest() throws Exception {
        // First book
        BookEntity book1 = new BookEntity("Tom Sawyer & Huckleberry Finn", "068424542", LocalDate.of(1970, 1, 1), 150, Language.ENGLISH, Genre.CRIME);
        booksRepository.save(book1);

        // Second book
        BookEntity book2 = new BookEntity("1983", "0451524934", LocalDate.of(1949, 6, 8), 328, Language.ENGLISH, Genre.DYSTOPIAN);
        booksRepository.save(book2);

        // Perform the request and check both books
        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", Matchers.is(book1.getTitle())))
                .andExpect(jsonPath("$[0].isbn", Matchers.is(book1.getIsbn())))
                .andExpect(jsonPath("$[0].datePublished", Matchers.is("1970-01-01")))
                .andExpect(jsonPath("$[0].pages", Matchers.is(book1.getPages())))
                .andExpect(jsonPath("$[0].language", Matchers.is(book1.getLanguage().getDisplayName())))
                .andExpect(jsonPath("$[0].genre", Matchers.is(book1.getGenre().getDisplayName())))
                .andExpect(jsonPath("$[1].title", Matchers.is(book2.getTitle())))
                .andExpect(jsonPath("$[1].isbn", Matchers.is(book2.getIsbn())))
                .andExpect(jsonPath("$[1].datePublished", Matchers.is("1949-06-08")))
                .andExpect(jsonPath("$[1].pages", Matchers.is(book2.getPages())))
                .andExpect(jsonPath("$[1].language", Matchers.is(book2.getLanguage().getDisplayName())))
                .andExpect(jsonPath("$[1].genre", Matchers.is(book2.getGenre().getDisplayName())));
    }

    // *********** //
    // POST /books //
    // *********** //

    @Test
    public void testCreateBookSuccess() throws Exception {
        String jsonBookDto = mapper.writeValueAsString(new BookDto("1984", "9783730609767", LocalDate.of(1984, 12, 1), 984, Language.ENGLISH, Genre.FANTASY));

        mvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("1984"));
    }

    @Test
    public void testCreateBook_AlreadyExists() throws Exception {
        String title = "Animal Farm";
        String isbn = "9783903352780";
        LocalDate datePublished = LocalDate.of(1984, 12, 1);
        int pages = 984;
        Language language = Language.ENGLISH;
        Genre genre = Genre.FANTASY;

        String jsonBookDto = mapper.writeValueAsString(new BookDto(title, isbn, datePublished, pages, language, genre));

        booksRepository.save(new BookEntity(title, isbn, datePublished, pages, language, genre));

        mvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value("Animal Farm"));
    }

    @Test
    public void testCreateBook_invalidIsbn() throws Exception {
        String jsonBookDto = mapper.writeValueAsString(new BookDto("The Book", "123456789", LocalDate.of(1970, 1, 1), 10, Language.ENGLISH, Genre.CRIME));

        mvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("123456789 is not a valid ISBN."));
    }

    @Test
    public void testCreateBook_noIsbn() throws Exception {
        String jsonBookDto = mapper.writeValueAsString(new BookDto("The Book", null, LocalDate.of(1970, 1, 1), 10, Language.ENGLISH, Genre.CRIME));

        mvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Missing ISBN"));
    }

    // ***************** //
    // GET /books/{isbn} //
    // ***************** //

    @Test
    public void testGetBook_invalidIsbn() throws Exception {
        String isbn = "123456789";
        String jsonBookDto = mapper.writeValueAsString(new BookDto("The Book", isbn, LocalDate.of(1970, 1, 1), 10, Language.ENGLISH, Genre.CRIME));

        mvc.perform(get("/books/" + isbn))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("123456789 is not a valid ISBN."));
    }

    @Test
    public void testGetBook_validIsbn() throws Exception {
        String isbn = "9781473224469";

        BookEntity book = new BookEntity("Dune", isbn);
        booksRepository.save(book);

        mvc.perform(get("/books/" + isbn))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Dune"));
    }

    @Test
    public void testGetBook_notFound() throws Exception {
        String isbn = "9783902980816";
        mvc.perform(get("/books/" + isbn))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Book not found x_x"));
    }

    // ********** //
    // PUT /books //
    // ********** //

    @Test
    public void testUpdateBook_NotExisting() throws Exception {
        String jsonBookDto = mapper.writeValueAsString(new BookDto("The Book", "979-8883749420", LocalDate.of(1970, 1, 1), 10, Language.ENGLISH, Genre.CRIME));

        mvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Book does not exist …"));
    }

    @Test
    public void testUpdateBook_Existing() throws Exception {
        String jsonBookDto = mapper.writeValueAsString(new BookDto("The Book", "979-8883749420", LocalDate.of(1970, 1, 1), 10, Language.ENGLISH, Genre.CRIME));

        BookEntity book = new BookEntity("Some title", "9798883749420");
        booksRepository.save(book);

        mvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("The Book"));
    }

    // ****************************** //
    // GET /books/{isbn}/compensation //
    // ****************************** //

    @Test
    public void testGetCompensation_invalidIsbn() throws Exception {
        String isbn = "123456789";
        String jsonBookDto = mapper.writeValueAsString(new BookDto("The Book", isbn, LocalDate.of(1970, 1, 1), 10, Language.ENGLISH, Genre.CRIME));

        mvc.perform(get("/books/" + isbn + "/compensation"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("123456789 is not a valid ISBN."));
    }

    @Test
    public void testGetCompensation_notFound() throws Exception {
        String isbn = "9783902980816";
        mvc.perform(get("/books/" + isbn + "/compensation"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Book not found x_x"));
    }

    @Test
    public void testGetCompensation_bookExists() throws Exception {
        String title = "Necronomicon";
        String isbn = "9780575081567";
        LocalDate datePublished = LocalDate.of(2008, 3, 27);
        int pages = 666;
        Language language = Language.ENGLISH;
        Genre genre = Genre.FANTASY;

        booksRepository.save(new BookEntity(title, isbn, datePublished, pages, language, genre));

        mvc.perform(get("/books/" + isbn + "/compensation")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(isbn))
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.compensation.amount").value(150))
                .andExpect(jsonPath("$.compensation.currency").value("EUR"))
                .andExpect(jsonPath("$.compensation.details.baseCompensation").value(100))
                .andExpect(jsonPath("$.compensation.details.ageCompensation").value(0))
                .andExpect(jsonPath("$.compensation.details.pageCompensationFactor").value(1.5))
                .andExpect(jsonPath("$.compensation.details.languageCompensationFactor").value(1));
    }

    @Test
    public void testGetCompensation_bookExistsIncompleteData() throws Exception {
        String title = "Sherlock Holmes";
        String isbn = "9783730610275";
        int pages = 666;
        Language language = Language.ENGLISH;
        Genre genre = Genre.FANTASY;

        BookEntity book = new BookEntity();

        book.setIsbn(isbn);
        book.setTitle(title);

        booksRepository.save(book);

        mvc.perform(get("/books/" + isbn + "/compensation")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("There is missing required metadata to calculate the compensation."));
    }
}