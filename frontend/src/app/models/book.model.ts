import { Compensation } from './compensation.model';

/**
 * Represents a BookEntity in the frontend including the compensation.
 */
export interface Book {
  title: string;
  isbn: string;
  publisher?: string | null;
  datePublished?: string | null;
  pages?: number | null;
  language?: string | null;
  genre?: string | null;
  compensation?: Compensation | null;
}
