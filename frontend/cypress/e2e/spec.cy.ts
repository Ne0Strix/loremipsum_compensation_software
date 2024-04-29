describe('Starting Layout Test', () => {
  beforeEach(() => {
    // This runs before each test in the block
    cy.visit('http://localhost:4200'); // Replace with the actual URL
  });

  it('Visits the "all books" page', () => {
    cy.url().should('include', '/books'); // Validates if the URL is correct
    cy.contains('All Books').should('be.visible'); // Checks if 'All Books' text is visible
  });

  it('Finds navigation buttons', () => {
    cy.get('.navigation').contains('Create Book').should('be.visible');
    cy.get('.navigation').contains('All Books').should('be.visible');
  });

  it('Changes to "Create Book" page', () => {
    cy.get('.navigation').contains('Create Book').click();
    cy.url().should('include', '/create');
    cy.wait(500);
    cy.get('[data-cy=bookForm]').should('be.visible');
  });

  it('Changes to "All Books" page', () => {
    cy.visit('http://localhost:4200/create');
    cy.get('.navigation').contains('All Books').click();
    cy.url().should('include', '/books');
    cy.get('[data-cy=bookTable]').should('be.visible');
  });
});

describe('All books test', () => {
  beforeEach(() => {
    cy.visit('http://localhost:4200');
  });

  it('Sees the "Show Details" arrow', () => {
    cy.get('[data-cy=toggle-details]').should('have.class', 'collapsed');
  });

  it('Clicks on the "Show Details" button', () => {
    cy.contains('ISBN').should('not.exist');
    cy.contains('Pages').should('not.exist');
    cy.contains('Date Published').should('not.be.exist');
    cy.contains('Language').should('not.be.exist');
    cy.contains('Compensation Amount').should('not.be.exist');
    cy.contains('Base Compensation').should('not.be.exist');
    cy.contains('Age Compensation').should('not.be.exist');
    cy.contains('Page Factor').should('not.be.exist');
    cy.contains('Language Factor').should('not.be.exist');

    cy.get('[data-cy=toggle-details]').first().click();

    cy.contains('ISBN').should('be.visible');
    cy.contains('Pages').should('be.visible');
    cy.contains('Date Published').should('be.visible');
    cy.contains('Language').should('be.visible');
    cy.contains('Compensation Amount').should('be.visible');
    cy.contains('Base Compensation').should('be.visible');
    cy.contains('Age Compensation').should('be.visible');
    cy.contains('Page Factor').should('be.visible');
    cy.contains('Language Factor').should('be.visible');
  });

  it('should expand only one row at a time', () => {
    // expand row details and verify indicator-class
    cy.get('[data-cy=toggle-details]').first().click();
    cy.get('[data-cy=toggle-details]').first().should('have.class', 'expanded');
    cy.get('[data-cy=toggle-details]').eq(1).should('have.class', 'collapsed');

    // expand second row and verify first closes
    cy.get('[data-cy=toggle-details]').eq(1).click();
    cy.get('[data-cy=toggle-details]')
      .first()
      .should('have.class', 'collapsed');
    cy.get('[data-cy=toggle-details]').eq(1).should('have.class', 'expanded');
  });

  it('should show book details when expanding', () => {
    cy.get('[data-cy=toggle-details]').first().click();

    cy.contains('ISBN').should('be.visible');
    cy.contains('Pages').should('be.visible');
    cy.contains('Date Published').should('be.visible');
    cy.contains('Language').should('be.visible');
    cy.contains('Compensation Amount').should('be.visible');
    cy.contains('Base Compensation').should('be.visible');
    cy.contains('Age Compensation').should('be.visible');
    cy.contains('Page Factor').should('be.visible');
    cy.contains('Language Factor').should('be.visible');
  });

  it('should hide book details when collapsing', () => {
    cy.get('[data-cy=toggle-details]').first().click();
    cy.get('[data-cy=toggle-details]').first().should('have.class', 'expanded');
    cy.get('[data-cy=toggle-details]').first().click();
    cy.get('[data-cy=toggle-details]')
      .first()
      .should('have.class', 'collapsed');

    cy.contains('ISBN').should('not.exist');
    cy.contains('Pages').should('not.exist');
    cy.contains('Date Published').should('not.be.exist');
    cy.contains('Language').should('not.be.exist');
    cy.contains('Compensation Amount').should('not.be.exist');
    cy.contains('Base Compensation').should('not.be.exist');
    cy.contains('Age Compensation').should('not.be.exist');
    cy.contains('Page Factor').should('not.be.exist');
    cy.contains('Language Factor').should('not.be.exist');
  });
});

describe('Create Book Test', () => {
  beforeEach(() => {
    cy.visit('http://localhost:4200/create');
  });

  it('should fill the form and show that it was succesfull', () => {
    cy.get('[formControlName=title]').type('New Book');
    cy.get('[formControlName=isbn]').type('9783446274297');
    cy.get('[formControlName=pages]').type('100');
    cy.get('[formControlName=genre]').click();
    cy.get('.p-dropdown-items').contains('Fiction').click();
    cy.get('[formControlName=language]').click();
    cy.get('.p-dropdown-items').contains('English').click();
    cy.get('[type=submit]').click();
    cy.contains('Success').should('be.visible');
  });

  it('adding a book again should show an error', () => {
    cy.get('[formControlName=title]').type('New Book');
    cy.get('[formControlName=isbn]').type('9783446274297');
    cy.get('[type=submit]').click();
    cy.contains('Error').should('be.visible');
  });

  it('should show an error when submitting an invalid isbn', () => {
    cy.get('[formControlName=title]').type('New Book');
    cy.get('[formControlName=isbn]').type('1234567654321');
    cy.get('[type=submit]').click();
    cy.contains('Error').should('be.visible');
  });
});
