import { Book } from './../../models/book.model';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { BookService } from '../../services/book.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-create-book',
  templateUrl: './create-book.component.html',
  styleUrl: './create-book.component.css',
  providers: [MessageService],
})
export class CreateBookComponent {
  genres: string[] = [];
  bookForm: FormGroup;
  languages: string[] = [];

  private bookService: BookService;
  private messageService: MessageService;

  constructor(bookService: BookService, messageService: MessageService) {
    this.bookService = bookService;
    this.messageService = messageService;

    this.bookService.getGenres().subscribe({
      next: (genres) => {
        this.genres = genres;
      },
      error: (error) => {
        console.error('Error fetching genres:', error);
      },
    });

    this.bookService.getLanguages().subscribe({
      next: (languages) => {
        this.languages = languages;
      },
      error: (error) => {
        console.error('Error fetching languages:', error);
      },
    });
    this.bookForm = new FormGroup({
      title: new FormControl('', Validators.required),
      isbn: new FormControl('', Validators.required),
      pages: new FormControl<number | null>(null),
      datePublished: new FormControl<string | null>(null),
      language: new FormControl<string | null>(null),
      genre: new FormControl<string | null>(null),
    });
  }

  onSubmit() {
    if (this.bookForm.valid) {
      this.bookService.createBook(this.bookForm.value).subscribe({
        next: (createdBook) => {
          console.log('New book created:', createdBook);
          this.messageService.add({
            severity: 'success',
            summary: 'Success',
            detail: 'Book created successfully',
          });
        },
        error: (error) => {
          console.error('Error creating book:', error);
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Failed to create book',
          });
        },
      });
    } else {
      this.messageService.add({
        severity: 'warn',
        summary: 'Validation Failed',
        detail: 'Form is not valid',
      });
    }
  }
}
