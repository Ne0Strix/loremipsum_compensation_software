package at.loremipsum.books.entities;

import at.loremipsum.books.dto.BookDto;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "book_entity")
public class BookEntity {
    @Id
    @Column(name = "isbn")
    private String isbn;
    @NotNull
    @Column(name = "title")
    private String title;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "date_published")
    private LocalDate datePublished;
    @Column(name = "pages")
    private int pages;
    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;
    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Genre genre;

    public BookEntity() {
    }

    public BookEntity(BookDto bookDto) {
        this.title = bookDto.getTitle();
        this.isbn = bookDto.getIsbn();
        this.publisher = bookDto.getPublisher();
        this.datePublished = bookDto.getDatePublished();
        this.pages = bookDto.getPages();
        this.language = bookDto.getLanguage();
        this.genre = bookDto.getGenre();
    }

    public BookEntity(String title, String isbn, LocalDate datePublished, int pages, Language language, Genre genre) {
        this.title = title;
        this.isbn = isbn;
        this.datePublished = datePublished;
        this.pages = pages;
        this.language = language;
        this.genre = genre;
    }

    public BookEntity(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
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


