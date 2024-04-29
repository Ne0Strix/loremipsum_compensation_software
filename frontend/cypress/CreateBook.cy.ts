import { MessageService } from 'primeng/api';

import { TestBed } from '@angular/core/testing';
import { CreateBookComponent } from '../src/app/components/create-book/create-book.component';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { InputMaskModule } from 'primeng/inputmask';
import { ButtonModule } from 'primeng/button';
import { ReactiveFormsModule } from '@angular/forms';
import { CalendarModule } from 'primeng/calendar';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumber, InputNumberModule } from 'primeng/inputnumber';
import { DropdownModule } from 'primeng/dropdown';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { BookService } from '../src/app/services/book.service';
import { of } from 'rxjs';
import { CardModule } from 'primeng/card';
import { ToastModule } from 'primeng/toast';

describe('CreateBookComponent', () => {
  beforeEach(() => {
    let component: CreateBookComponent;
    let bookService: BookService;
    let messageService: MessageService;


    const bookServiceStub = jasmine.createSpyObj('BookService', ['createBook']);
    const messageServiceStub = jasmine.createSpyObj('MessageService', ['add']);

    TestBed.configureTestingModule({
      declarations: [CreateBookComponent],
      providers: [
        { provide: BookService, useValue: bookServiceStub },
        { provide: MessageService, useValue: messageServiceStub },
      ],
    });

    const fixture = TestBed.createComponent(CreateBookComponent);
    component = fixture.componentInstance;

    bookService = TestBed.inject(BookService);
    messageService = TestBed.inject(MessageService);



    cy.mount(CreateBookComponent, {
      imports: [
        HttpClientTestingModule,
        InputMaskModule,
        ButtonModule,
        ReactiveFormsModule,
        CalendarModule,
        InputTextModule,
        InputNumberModule,
        DropdownModule,
        CardModule,
        ToastModule,
        NoopAnimationsModule,
      ],
      providers: [
        { provide: BookService, useValue: bookServiceStub },
        { provide: MessageService, useValue: messageServiceStub },
      ],
    });
  });

  it('should fill the form and call bookService.createBook with the form value', () => {
    cy.mount(CreateBookComponent);

    // Fill the form
    cy.get('[formControlName=title]').type('New Book');
    cy.get('[formControlName=isbn]').type('0000000000000');
    cy.get('[formControlName=pages]').type('100');

    cy.get('[formControlName=genre]').click();
    cy.get('.p-dropdown-items').contains('Fiction').click();

    cy.get('[formControlName=language]').click();
    cy.get('.p-dropdown-items').contains('English').click();

    cy.get('[formControlName=datePublished]').type('1970-01-01');
    cy.get('[formControlName=datePublished]').click();

    // Trigger the form submission that calls onSubmit()
    cy.get('form').submit();

    expect(cy.get('@createBookStub')).to.be.called;
  });

  it('should call messageService.add with success on successful book creation', () => {
    // Setup the stub to resolve with specific data
    cy.get('@createBookStub').resolves({ id: 123, title: 'New Book' });

    // Trigger the form submission that calls onSubmit()
    cy.get('form').submit();

    // Check if messageService.add() was called correctly
    cy.get('@addMessageStub').should('be.calledWith', {
      severity: 'success',
      summary: 'Success',
      detail: 'Book created successfully',
    });
  });
});
