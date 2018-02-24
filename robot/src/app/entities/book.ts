export interface Book {
  title: string,
  author: string,
  bookStore: string,
  bookDetails: {
    basePrice: number,
    promoPrice: number,
    genre: string,
  }
}
