import { Compensation } from './compensation.model';

export interface Book {
  title: string;
  isbn: string;
  publisher: string;
  datePublished?: string | null;
  pages?: number | null;
  language?: string | null;
  genre?: string | null;
  compensation?: Compensation | null;
}
