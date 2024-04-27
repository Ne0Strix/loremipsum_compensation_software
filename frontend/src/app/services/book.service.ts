import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Book } from '../models/book.model';
import { BookCompensation } from '../models/compensation.model';


@Injectable({
  providedIn: 'root',
})
export class BookService {
  private apiBaseUrl = 'http://127.0.0.1:8080/books';

  constructor(private http: HttpClient) {}

  getBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(this.apiBaseUrl);
  }

  getCompensationByIsbn(isbn: string): Observable<BookCompensation> {
    return this.http.get<BookCompensation>(`${this.apiBaseUrl}/${isbn}/compensation`);
  }

  createBook(book: any): Observable<any> {
    return this.http.post<any>(this.apiBaseUrl, book);
  }
}
