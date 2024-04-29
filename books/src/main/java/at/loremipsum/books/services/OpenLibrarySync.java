package at.loremipsum.books.services;

import at.loremipsum.books.entities.BookEntity;
import at.loremipsum.books.entities.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;

@Service
public class OpenLibrarySync {
    private final String REQUEST_URL = "https://openlibrary.org/isbn/";

    @Autowired
    private BooksRepository repo;


    private void updateBooks(){
        int page = 0;
        int size = 50; // Number of records per page

        Pageable pageable = PageRequest.of(page, size);
        Slice<BookEntity> bookSlice;
        //Slice<BookEntity> toUpdate = repo.findFirst10BookEntityByIsEnrichedIsFalse(pageable);

        //toUpdate.get().limit(10).forEach(this::updateData);
        // toUpdate.next()
    }

    private void updateData(BookEntity book){

    }
}
