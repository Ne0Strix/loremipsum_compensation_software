package at.loremipsum.books.rest;

import at.loremipsum.books.BooksApplication;
import at.loremipsum.books.entities.BookEntity;
import at.loremipsum.books.entities.BooksRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = BooksApplication.class)
@AutoConfigureMockMvc

public class BooksTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BooksRepository booksRepository;

    @Test
    public void test() throws Exception {
        BookEntity book = new BookEntity();
        book.setTitle("Tom Sawyer & Huckleberry Finn");
        book.setIsbn("068424542");
        booksRepository.save(book);

        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", Matchers.is(book.getTitle())));
    }
}