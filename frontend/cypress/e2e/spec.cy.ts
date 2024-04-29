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
  });

  it('Sees the "Show Details" arrow', () => {
    cy.get('[data-cy=toggle-details]').should('have.class', 'collapsed');
  });
});

describe('Book Details Test', () => {
  beforeEach(() => {
    cy.visit('http://localhost:4200');
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

  describe('Table Row Expansion and Collapse', () => {
    beforeEach(() => {
      cy.visit('http://localhost:4200');
    });

    it('should expand and collapse the row details on button click', () => {
      // expand row details and verify indicator-class
      cy.get('[data-cy=toggle-details]').first().click();
      cy.get('[data-cy=toggle-details]')
        .first()
        .should('have.class', 'expanded');
      cy.get('[data-cy=toggle-details]')
        .eq(1)
        .should('have.class', 'collapsed');

      // Collapse row details and verify details are hidden
      cy.get('[data-cy=toggle-details]').first().click();

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
});
