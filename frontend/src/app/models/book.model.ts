import { Compensation } from './compensation.model';

export interface Book {
  title: string;
  isbn: string;
  datePublished?: string | null;
  pages?: number | null;
  language?: string | null;
  genre?: string | null;
  compensation?: Compensation | null;
}
