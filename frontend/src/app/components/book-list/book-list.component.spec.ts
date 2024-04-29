import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookListComponent } from './book-list.component';
import { BookService } from '../../services/book.service';
import { of } from 'rxjs';
import { TableModule } from 'primeng/table';
import { CardModule } from 'primeng/card';
import { PrimeIcons } from 'primeng/api';

class mockBoockService {
  getBooks = jest.fn().mockReturnValue(
    of([
      { title: 'Test Book', isbn: '0000000000000' },
      { title: 'Test Book 2', isbn: '0000000000001' },
    ])
  );
  getCompensationByIsbn = jest.fn().mockReturnValue(of({ compensation: 10 }));
}

describe('BookListComponent', () => {
  let component: BookListComponent;
  let fixture: ComponentFixture<BookListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BookListComponent],
      imports: [TableModule, CardModule],
      providers: [{ provide: BookService, useClass: mockBoockService }],
    }).compileComponents();

    fixture = TestBed.createComponent(BookListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get compensation', () => {
    expect(component.books[0].compensation).toEqual(10);
  });

  it('should get books', () => {
    expect(component.books[0].title).toEqual('Test Book');
    expect(component.books[0].isbn).toEqual('0000000000000');
    expect(component.books[1].title).toEqual('Test Book 2');
    expect(component.books[1].isbn).toEqual('0000000000001');
  });
});
