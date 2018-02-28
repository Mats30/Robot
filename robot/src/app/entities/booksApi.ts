import {Book} from "./book";

export interface BooksApi {
  content: Book[],
  totalElements: number
}
