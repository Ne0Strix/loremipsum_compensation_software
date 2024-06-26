package at.loremipsum.books.rest;

import at.loremipsum.books.dto.BookDto;
import at.loremipsum.books.dto.CompensationDto;
import at.loremipsum.books.entities.BookEntity;
import at.loremipsum.books.entities.BooksRepository;
import at.loremipsum.books.entities.Genre;
import at.loremipsum.books.entities.Language;
import at.loremipsum.books.exceptions.InvalidDataException;
import at.loremipsum.books.services.BookPriceCalculator;
import at.loremipsum.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/books")
@CrossOrigin(origins = "http://localhost:4200")
public class BooksController {
    @Autowired
    private BookService bookService;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private BookPriceCalculator calculator;

    /**
     * Retrieves a list of all books in the repository.
     *
     * @return A ResponseEntity containing a list of BookDto objects.
     */
    @GetMapping()
    public ResponseEntity<List<BookDto>> getBooks() {
        Iterable<BookEntity> bookEntities = booksRepository.findAll();
        List<BookDto> bookDtos = new ArrayList<>();

        for (BookEntity bookEntity : bookEntities) {
            bookDtos.add(new BookDto(bookEntity));
        }

        return ResponseEntity.status(HttpStatus.OK).body(bookDtos);
    }

    /**
     * Creates a new book entry in the repository.
     *
     * @param bookDto The BookDto containing the book data to create.
     * @return A ResponseEntity containing the created BookDto or conflict if exists.
     */
    @PostMapping()
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookEntity book = verifyRequest(bookDto);

        Optional<BookEntity> existingBook = booksRepository.findById(book.getIsbn());

        if (existingBook.isPresent()) {
            BookDto existingEntry = new BookDto(existingBook.get());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(existingEntry);
        }

        book = booksRepository.save(book);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BookDto(book));
    }

    /**
     * Retrieves a specific book by its ISBN.
     *
     * @param isbn The ISBN to search for.
     * @return A ResponseEntity with the found BookDto or an error message if not found.
     */
    @GetMapping("/{isbn}")
    public ResponseEntity<?> getBookByIsbn(@PathVariable String isbn) {
        // invalid ISBN
        if (!bookService.validateIsbn(isbn)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(isbn + " is not a valid ISBN.");
        }
        String normalizedIsbn = bookService.normalizeIsbn(isbn);

        // valid ISBN
        Optional<BookEntity> book = booksRepository.findById(normalizedIsbn);
        if (book.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new BookDto(book.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found x_x");
        }
    }

    /**
     * Calculates and retrieves compensation details for a book by ISBN.
     *
     * @param isbn The ISBN of the book to calculate compensation for.
     * @return A ResponseEntity containing compensation details or an error if not found.
     */
    @GetMapping("/{isbn}/compensation")
    public ResponseEntity<?> getCompensationByIsbn(@PathVariable String isbn) {
        // invalid ISBN
        if (!bookService.validateIsbn(isbn)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(isbn + " is not a valid ISBN.");
        }
        String normalizedIsbn = bookService.normalizeIsbn(isbn);

        // valid ISBN
        Optional<BookEntity> book = booksRepository.findById(normalizedIsbn);
        if (book.isPresent()) {

            BookEntity source = book.get();
            if (source.getDatePublished() == null || source.getLanguage() == null) {
                throw new InvalidDataException("There is missing required metadata to calculate the compensation.");
            }

            CompensationDto compensationDto = new CompensationDto();
            CompensationDto.Compensation compensation = new CompensationDto.Compensation();
            CompensationDto.Compensation.CompensationDetails details = new CompensationDto.Compensation.CompensationDetails();

            //book metadata
            compensationDto.setIsbn(normalizedIsbn);
            compensationDto.setTitle(source.getTitle());

            //compensation details
            details.setBaseCompensation(calculator.getBaseCompensation());
            details.setAgeCompensation(calculator.getAgeCompensation(source.getDatePublished().getYear()));
            details.setPageCompensationFactor(calculator.getCompensationFactor(source.getPages()));
            details.setLanguageCompensationFactor(calculator.getLanguageCompensationFactor(source.getLanguage()));
            compensation.setDetails(details);

            //compensation
            compensation.setAmount(calculator.getCompensation(source));
            compensation.setCurrency("EUR");
            compensationDto.setCompensation(compensation);

            return ResponseEntity.status(HttpStatus.OK).body(compensationDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found x_x");
        }
    }

    /**
     * Updates an existing book in the repository.
     *
     * @param bookDto The BookDto containing updated data.
     * @return A ResponseEntity with the updated BookDto or an error message if the book does not exist.
     */
    @PutMapping
    public ResponseEntity<?> updateBook(@RequestBody BookDto bookDto) {
        BookEntity book = verifyRequest(bookDto);

        Optional<BookEntity> existingBook = booksRepository.findById(book.getIsbn());

        if (existingBook.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book does not exist …");
        } else {
            book = booksRepository.save(book);

            return ResponseEntity.status(HttpStatus.OK).body(new BookDto(book));
        }
    }

    /**
     * Retrieves all supported languages.
     *
     * @return A ResponseEntity containing a list of Language enums.
     */
    @GetMapping("/languages")
    public ResponseEntity<List<Language>> getLanguages() {
        return ResponseEntity.status(HttpStatus.OK).body(Arrays.asList(Language.values()));
    }

    /**
     * Retrieves all supported book genres.
     *
     * @return A ResponseEntity containing a list of Genre enums.
     */
    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getGenres() {
        return ResponseEntity.status(HttpStatus.OK).body(Arrays.asList(Genre.values()));
    }

    /**
     * Verifies and processes the incoming bookDto to ensure it is valid.
     *
     * @param bookDto The BookDto to verify.
     * @return A BookEntity based on the verified BookDto.
     */
    private BookEntity verifyRequest(BookDto bookDto) {
        bookService.validateBooksDto(bookDto);
        bookDto.setIsbn(bookService.normalizeIsbn(bookDto.getIsbn()));
        return new BookEntity(bookDto);
    }
}
