import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Book } from '../models/book.model';
import { BookCompensation } from '../models/compensation.model';
import { environment } from '../../../environment';

@Injectable({
  providedIn: 'root',
})
/**
 * Service for handling book-related operations with a remote API.
 * Provides methods to get, create books and fetch related metadata like genres and languages.
 */
export class BookService {
  constructor(private http: HttpClient) {}

  /**
   * Retrieves array of all books from the server.
   * @returns An Observable array of Book objects.
   */
  getBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(`${environment.apiBaseUrl}/books`);
  }

  /**
   * Fetches compensation details for a specific book by ISBN.
   * @param isbn The ISBN of the book to retrieve compensation for.
   * @returns An Observable of BookCompensation object.
   */
  getCompensationByIsbn(isbn: string): Observable<BookCompensation> {
    return this.http.get<BookCompensation>(
      `${environment.apiBaseUrl}/books/${isbn}/compensation`
    );
  }

  /**
   * Submits a new book to the server.
   * @param book The book object to create.
   * @returns An Observable of reflecting the API-response.
   */
  createBook(book: any): Observable<any> {
    return this.http.post<any>(`${environment.apiBaseUrl}/books`, book);
  }

  /**
   * Retrieves a list of book genres from the server.
   * @returns An Observable array of genre strings.
   */
  getGenres(): Observable<string[]> {
    return this.http.get<string[]>(`${environment.apiBaseUrl}/books/genres`);
  }

  /**
   * Retrieves a list of supported languages from the server.
   * @returns An Observable array of language strings.
   */
  getLanguages(): Observable<string[]> {
    return this.http.get<string[]>(`${environment.apiBaseUrl}/books/languages`);
  }
}
