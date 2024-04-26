package at.loremipsum.books.dto;

import at.loremipsum.books.entities.BookEntity;

import java.time.LocalDate;

public class BookDto {
    private String title;
    private String isbn;
    private LocalDate datePublished;
    private String publisher;
    private int pages;
    private String language;
    private String genre;
    private float compensation;

    public BookDto() {
    }

    public BookDto(String title, String isbn, LocalDate datePublished, int pages, String language, String genre) {
        this.title = title;
        this.isbn = isbn;
        this.datePublished = datePublished;
        this.pages = pages;
        this.language = language;
        this.genre = genre;
    }

    public BookDto(BookEntity book) {
        this.title = book.getTitle();  // Assume getTitle never returns null
        this.isbn = book.getIsbn();    // Assume getIsbn never returns null
        this.publisher = book.getPublisher() != null ? book.getPublisher() : null;
        this.datePublished = book.getDatePublished() != null ? book.getDatePublished() : null;
        this.pages = book.getPages();
        this.language = book.getLanguage() != null ? book.getLanguage().getCode() : null;
        this.genre = book.getGenre() != null ? book.getGenre().getDisplayName() : null;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public float getCompensation() {
        return this.compensation;
    }

    public void setCompensation(float compensation) {
        this.compensation = compensation;
    }
}
