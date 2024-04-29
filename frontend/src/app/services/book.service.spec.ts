import { TestBed, inject } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { BookService } from './book.service';
import { environment } from '../../../environment';

describe('BookService', () => {
  let service: BookService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [BookService],
    });
    service = TestBed.inject(BookService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  // afterEach(() => {
  //   httpMock.verify();
  // });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get list of books', () => {
    const mockBooks = [{ title: 'Book 1' }, { title: 'Book 2' }];

    service.getBooks().subscribe((books) => {
      expect(books.length).toBe(2);
      expect(books).toEqual(mockBooks);
    });

    const req = httpMock.expectOne(`${environment.apiBaseUrl}/books`);
    expect(req.request.method).toBe('GET');
    req.flush(mockBooks);
  });

  it('should get compensation by ISBN', () => {
    const mockCompensation = { amount: 10, currency: 'EUR', details: {} };

    let isbn: string = '0000000000000';

    service.getCompensationByIsbn(isbn).subscribe((compensation) => {
      expect(compensation).toEqual(mockCompensation);
    });

    const req = httpMock.expectOne(
      `${environment.apiBaseUrl}/books/${isbn}/compensation`
    );
    expect(req.request.method).toBe('GET');
    req.flush(mockCompensation);
  });

  it('should create a new book', () => {
    const newBook = { title: 'New Book', author: 'Author' };

    service.createBook(newBook).subscribe((response) => {
      expect(response).toBeTruthy();
    });

    const req = httpMock.expectOne(`${environment.apiBaseUrl}/books`);
    expect(req.request.method).toBe('POST');
    req.flush({});
  });

  it('should get list of genres', () => {
    const mockGenres = ['Fiction', 'Non-Fiction', 'Mystery'];

    service.getGenres().subscribe((genres) => {
      expect(genres.length).toBe(3);
      expect(genres).toEqual(mockGenres);
    });

    const req = httpMock.expectOne(`${environment.apiBaseUrl}/books/genres`);
    expect(req.request.method).toBe('GET');
    req.flush(mockGenres);
  });

  it('should get list of languages', () => {
    const mockLanguages = ['English', 'French', 'Spanish'];

    service.getLanguages().subscribe((languages) => {
      expect(languages.length).toBe(3);
      expect(languages).toEqual(mockLanguages);
    });

    const req = httpMock.expectOne(`${environment.apiBaseUrl}/books/languages`);
    expect(req.request.method).toBe('GET');
    req.flush(mockLanguages);
  });
});
