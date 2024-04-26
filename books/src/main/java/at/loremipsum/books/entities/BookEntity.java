package at.loremipsum.books.entities;

import at.loremipsum.books.dto.BookDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Entity
public class BookEntity {
    @NotNull
    private String title;
    @Id
    private String isbn;
    private LocalDate datePublished;
    private int pages;
    private Language language;
    private Genre genre;

    public BookEntity() {
    }

    public BookEntity(BookDto bookDto) {
        this.title = bookDto.getTitle();
        this.isbn = bookDto.getIsbn();
        this.datePublished = bookDto.getDatePublished();
        this.pages = bookDto.getPages();
        this.language = Language.fromCode(bookDto.getLanguage());
        this.genre = Genre.fromString(bookDto.getGenre());
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


