package at.loremipsum.books.dto;

import at.loremipsum.books.entities.BookEntity;
import at.loremipsum.books.entities.Genre;
import at.loremipsum.books.entities.Language;

import java.time.LocalDate;

/**
 * Data Transfer Object representing a book's data.
 * This class simplifies client-server communication by carrying data between processes.
 */
public class BookDto {
    private String title;
    private String isbn;
    private LocalDate datePublished;
    private String publisher;
    private int pages;
    private Language language;
    private Genre genre;

    /**
     * Constructs an empty BookDto.
     */
    public BookDto() {
    }

    /**
     * Constructs a BookDto with specified book details.
     *
     * @param title         The title of the book.
     * @param isbn          The ISBN of the book.
     * @param datePublished The publication date of the book.
     * @param pages         The number of pages in the book.
     * @param language      The language in which the book is written.
     * @param genre         The genre of the book.
     */
    public BookDto(String title, String isbn, LocalDate datePublished, int pages, Language language, Genre genre) {
        this.title = title;
        this.isbn = isbn;
        this.datePublished = datePublished;
        this.pages = pages;
        this.language = language;
        this.genre = genre;
    }

    /**
     * Constructs a BookDto from a BookEntity, copying its properties.
     *
     * @param book The BookEntity to copy from.
     */
    public BookDto(BookEntity book) {
        this.title = book.getTitle();  // Assume getTitle never returns null
        this.isbn = book.getIsbn();    // Assume getIsbn never returns null
        this.publisher = book.getPublisher() != null ? book.getPublisher() : null;
        this.datePublished = book.getDatePublished() != null ? book.getDatePublished() : null;
        this.pages = book.getPages();
        this.language = book.getLanguage() != null ? book.getLanguage() : null;
        this.genre = book.getGenre() != null ? book.getGenre() : null;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(LocalDate datePublished) {
        this.datePublished = datePublished;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
