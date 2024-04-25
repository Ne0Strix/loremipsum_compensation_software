package at.loremipsum.books.entities;

import at.loremipsum.books.dto.BookDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;

// TODO with hibernate
@Entity
public class BookEntity {
    @NotNull
    private String title;
    @Id
    private String isbn;
    private Date yearPublished;
    private int pages;
    private String language;
    private String genre;

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

    public Date getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(Date yearPublished) {
        this.yearPublished = yearPublished;
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

    public static BookEntity getEntityFromDto(BookDto bookDto) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(bookDto.getTitle());
        bookEntity.setIsbn(bookDto.getIsbn());
        bookEntity.setYearPublished(bookDto.getYearPublished());
        bookEntity.setPages(bookDto.getPages());
        bookEntity.setLanguage(bookDto.getLanguage());
        bookEntity.setGenre(bookDto.getGenre());

        return bookEntity;
    }

    public BookDto convertToDto() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle(this.getTitle());
        bookDto.setIsbn(this.getIsbn());
        bookDto.setYearPublished(this.getYearPublished());
        bookDto.setPages(this.getPages());
        bookDto.setLanguage(this.getLanguage());
        bookDto.setGenre(this.getLanguage());

        return bookDto;
    }

}


