package at.loremipsum.books.rest;

import at.loremipsum.books.dto.BookDto;
import at.loremipsum.books.dto.DtoValidator;
import at.loremipsum.books.entities.BookEntity;
import at.loremipsum.books.entities.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/books")
public class Books {
    @Autowired
    private DtoValidator dtoValidator;

    @Autowired
    private BooksRepository booksRepository;

    @GetMapping()
    public ResponseEntity<List<BookDto>> getBooks() {
        Iterable<BookEntity> bookEntities = booksRepository.findAll();
        List<BookDto> bookDtos = new ArrayList<>();

        for (BookEntity bookEntity : bookEntities) {
            bookDtos.add(bookEntity.convertToDto());
        }

        return ResponseEntity.status(HttpStatus.OK).body(bookDtos);
    }

    @PostMapping()
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {

        // validate data
        dtoValidator.validateBooksDto(bookDto);

        // convert DTO to entity
        BookEntity entity = BookEntity.getEntityFromDto(bookDto);

        // write to DB
        entity = booksRepository.save(entity);

        // send return value
        return ResponseEntity.status(HttpStatus.CREATED).body(entity.convertToDto());
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<?> getBookByIsbn(@PathVariable String isbn) {
        Optional<BookEntity> book = booksRepository.findById(isbn);
        if (book.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(book.get().convertToDto());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found x_x");
        }
    }
}
