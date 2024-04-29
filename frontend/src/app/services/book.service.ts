import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Book } from '../models/book.model';
import { BookCompensation } from '../models/compensation.model';
import { environment } from '../../../environment';

@Injectable({
  providedIn: 'root',
})
export class BookService {
  constructor(private http: HttpClient) {}

  getBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(`${environment.apiBaseUrl}/books`);
  }

  getCompensationByIsbn(isbn: string): Observable<BookCompensation> {
    return this.http.get<BookCompensation>(
      `${environment.apiBaseUrl}/books/${isbn}/compensation`
    );
  }

  createBook(book: any): Observable<any> {
    return this.http.post<any>(`${environment.apiBaseUrl}/books`, book);
  }

  getGenres(): Observable<string[]> {
    return this.http.get<string[]>(`${environment.apiBaseUrl}/books/genres`);
  }

  getLanguages(): Observable<string[]> {
    return this.http.get<string[]>(`${environment.apiBaseUrl}/books/languages`);
  }
}
