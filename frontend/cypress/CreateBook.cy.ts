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
import { createOutputSpy } from 'cypress/angular';

class BookServiceStub {
  createBook() {
    return of({ id: 123, title: 'New Book' });
  }
  getGenres() {
    return of(['Fiction', 'Non-fiction']);
  }
  getLanguages() {
    return of(['English', 'Spanish']);
  }
}

class MessageServiceStub {
  add() {}
}

describe('CreateBookComponent', () => {
  beforeEach(() => {});

  it('should fill the form and call bookService.createBook and messageService.add', () => {
    const createBookStub = cy
      .stub()
      .returns(of({ id: 123, title: 'New Book' }));

    const addMessageStub = cy.stub().returns(of({}));

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
      componentProperties: {
        bookService: {
          createBook: createBookStub,
          getGenres: cy.stub(),
          getLanguages: cy.stub(),
        },
        messageService: {
          add: addMessageStub,
        },
        languages: ['English', 'Spanish'],
        genres: ['Fiction', 'Non-fiction'],
      },
    });

    cy.wrap(addMessageStub).as('addMessageStub');

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

    cy.wrap(createBookStub).should('have.been.called');
    cy.get('@addMessageStub').should('have.been.calledWith', {
      severity: 'success',
      summary: 'Success',
      detail: 'Book created successfully',
    });
  });

  it('should call messageService.add with error on failed book creation', () => {
    const createBookStub = cy.stub().returns({
      subscribe: (observer) => {
        // Check if the observer has an error method or is itself an error handling function
        const errorHandler =
          typeof observer === 'function' ? observer : observer.error;
        if (errorHandler) {
          errorHandler(new Error('Failed to create book'));
        }
      },
    });
    const addMessageStub = cy.stub().returns(of({}));

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
      componentProperties: {
        bookService: {
          createBook: createBookStub,
          getGenres: cy.stub(),
          getLanguages: cy.stub(),
        },
        messageService: {
          add: addMessageStub,
        },
        languages: ['English', 'Spanish'],
        genres: ['Fiction', 'Non-fiction'],
      },
    });

    cy.wrap(addMessageStub).as('addMessageStub');

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

    cy.get('form').submit();

    cy.get('@addMessageStub').should('have.been.calledWith', {
      severity: 'error',
      summary: 'Error',
      detail: 'Failed to create book',
    });
  });

  it('should indicate that the form is invalid', () => {
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
      componentProperties: {
        bookService: {
          createBook: cy.stub(),
          getGenres: cy.stub(),
          getLanguages: cy.stub(),
        },
        messageService: {
          add: cy.stub,
        },
        languages: ['English', 'Spanish'],
        genres: ['Fiction', 'Non-fiction'],
      },
    });

    cy.contains('invalid').should('be.visible');
    cy.get('[formControlName=title]').type('New Book');
    cy.contains('invalid').should('be.visible');
    cy.get('[formControlName=isbn]').type('0000000000000');
    cy.contains('is valid').should('be.visible');
  });
});
