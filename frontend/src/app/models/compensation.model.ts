/**
 * Model for compensation of a book.
 */

export interface BookCompensation {
  isbn: string;
  title: string;
  compensation: Compensation;
}

export interface Compensation {
  amount: number;
  currency: string;
  details: CompensationDetails;
}

export interface CompensationDetails {
  baseCompensation: number;
  ageCompensation: number;
  pageCompensationFactor: number;
  languageCompensationFactor: number;
}
