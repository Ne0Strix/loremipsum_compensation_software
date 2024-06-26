import { Component, OnInit } from '@angular/core';
import { BookService } from '../../services/book.service';
import { Book } from '../../models/book.model';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.css',
})

/**
 * Component responsible for displaying a list of books.
 * It fetches all books from the server and displays them in a table.
 */
export class BookListComponent implements OnInit {
  books: Book[] = [];
  cols: any[];

  isLoading: boolean = true;
  error: string | null = null;
  expanded: boolean = false;

  constructor(private bookService: BookService) {
    this.cols = [
      { field: 'title', header: 'Title' },
      { field: 'genre', header: 'Genre' },
      { field: 'compensation', subField: 'amount', header: 'Compensation' },
    ];
  }

  /**
   * Fetches all books from the server and their compensations.
   */
  ngOnInit(): void {
    this.bookService.getBooks().subscribe((books) => {
      this.books = books;
      this.books.forEach(
        (book) => {
          this.bookService
            .getCompensationByIsbn(book.isbn)
            .subscribe((compensation) => {
              book.compensation = compensation.compensation;
            });
        },
        (error: { message: string | null }) => {
          this.error = error.message;
          console.error(error);
          this.isLoading = false;
        }
      );
      this.isLoading = false;
    });
  }
}
