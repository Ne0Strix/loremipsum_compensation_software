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
  bookForm = new FormGroup({
    title: new FormControl('', Validators.required),
    isbn: new FormControl('', Validators.required),
    pages: new FormControl(null),
    datePublished: new FormControl(null),
    language: new FormControl(null),
    genre: new FormControl(null),
  });

  languages = [
    { label: 'English', value: 'English' },
    { label: 'German', value: 'German' },
    { label: 'Chinese', value: 'Chinese' },
    { label: 'Croatian', value: 'Croatian' },
    { label: 'French', value: 'French' },
    { label: 'Italian', value: 'Italian' },
    { label: 'Norwegian', value: 'Norwegian' },
    { label: 'Persian', value: 'Persian' },
    { label: 'Russian', value: 'Russian' },
    { label: 'Sanskrit', value: 'Sanskrit' },
    { label: 'Spanish', value: 'Spanish' },
    { label: 'Swedish', value: 'Swedish' },
    { label: 'Turkish', value: 'Turkish' },
    { label: 'Czech', value: 'Czech' },
  ];

  genres = [
    'Fiction',
    'Non-Fiction',
    'Mystery',
    'Thriller',
    'Horror',
    'Science Fiction',
    'Fantasy',
    'Romance',
    'Western',
    'Dystopian',
    'Contemporary',
    'Crime',
    'Adventure',
    'History',
    'Self Help',
    'Health',
    'Travel',
    "Children's",
    'Religion',
    'Science',
    'Humor',
    'Cookbooks',
    'Biography',
    'Autobiography',
    'Young Adult',
    'Paranormal',
    'Art',
    'Psychology',
    'Graphic Novel',
    'Poetry',
  ];

  constructor(
    private bookService: BookService,
    private messageService: MessageService
  ) {}

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
