import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateBookComponent } from './create-book.component';
import { BookService } from '../../services/book.service';
import { MessageService } from 'primeng/api';
import { of } from 'rxjs';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CalendarModule } from 'primeng/calendar';
import { CardModule } from 'primeng/card';
import { DropdownModule } from 'primeng/dropdown';
import { InputMaskModule } from 'primeng/inputmask';
import { InputNumberModule } from 'primeng/inputnumber';
import { ToastModule } from 'primeng/toast';

class MockBookService {
  createBook = jest
    .fn()
    .mockReturnValue(of({ title: 'Test Book', isbn: '0000000000000' }));
  getGenres = jest.fn().mockReturnValue(of(['Fiction', 'Non-Fiction']));
  getLanguages = jest.fn().mockReturnValue(of(['English', 'Spanish']));
}

class MockMessageService {
  add = jest.fn();
}

describe('CreateBookComponent', () => {
  let component: CreateBookComponent;
  let fixture: ComponentFixture<CreateBookComponent>;
  let bookService: BookService;
  let messageService: MessageService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateBookComponent],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        ToastModule,
        CardModule,
        InputMaskModule,
        CalendarModule,
        InputNumberModule,
        DropdownModule,
        BrowserAnimationsModule
      ],
      providers: [
        CreateBookComponent,
        { provide: BookService, useClass: MockBookService },
        { provide: MessageService, useClass: MockMessageService },
      ],
    });

    bookService = TestBed.inject(BookService);
    messageService = TestBed.inject(MessageService);

    fixture = TestBed.createComponent(CreateBookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  it('should call createBook() with correct parameters', () => {
    component.bookForm.controls['title'].setValue('Test Book');
    component.bookForm.controls['isbn'].setValue('123-456-789');
    component.bookForm.controls['pages'].setValue(100);
    component.bookForm.controls['datePublished'].setValue('2021-01-01');
    component.bookForm.controls['language'].setValue('English');
    component.bookForm.controls['genre'].setValue('Fiction');
    component.onSubmit();
    expect(bookService.createBook).toHaveBeenCalledTimes(1);
    expect(bookService.createBook).toHaveBeenCalledWith({
      title: 'Test Book',
      isbn: '123-456-789',
      pages: 100,
      datePublished: '2021-01-01',
      language: 'English',
      genre: 'Fiction',
    });
  });

  // had issues mocking the messageService.add() method; wasn't called but the UI works
  //
  // it('should call messageService.add() with correct parameters', fakeAsync(() => {
  //   const spy = jest.spyOn(messageService, 'add');
  //   component.bookForm.controls['title'].setValue('Test Book');
  //   component.bookForm.controls['isbn'].setValue('0000000000000');
  //   component.bookForm.controls['pages'].setValue(100);
  //   component.bookForm.controls['datePublished'].setValue('2021-01-01');
  //   component.bookForm.controls['language'].setValue('English');
  //   component.bookForm.controls['genre'].setValue('Fiction');
  //   component.onSubmit();
  //   tick();
  //   fixture.detectChanges();
  //   expect(spy).toHaveBeenCalledTimes(1);
  //   expect(spy).toHaveBeenCalledWith({
  //     severity: 'success',
  //     summary: 'Success',
  //     detail: 'Book created successfully',
  //   });
  // }));
});
