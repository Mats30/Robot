import {Injectable} from '@angular/core';
import {UtilityService} from "./utility.service";
import {Book} from "../entities/book";

@Injectable()
export class BookService {
  data: Array<Book>;

  constructor(private utilityService: UtilityService) {
    this.data = [];
  }

  public getBooksList() {
    /*
    Dummy objects inserted to data source to test sorting and filtering functionalities
     */
    let potop: Book = {
      title: "Potop",
      author: "Sienkiewicz",
      bookshop: "Bonito",
      promotion: "100 lat niepodległości",
      new_price: 49.99,
      old_price: 69.99
    };

    let potop2: Book = {
      title: "Potop",
      author: "Sienkiewicz",
      bookshop: "Helion",
      promotion: "Klasyka taniej",
      new_price: 46.99,
      old_price: 74.99
    };

    let witcher: Book = {
      title: "Wiedźmin",
      author: "Sapkowski",
      bookshop: "Bonito",
      promotion: "Fantastyka taniej",
      new_price: 29.99,
      old_price: 49.99
    };

    let java: Book = {
      title: "Effective Java",
      author: "Joshua Bloch",
      bookshop: "Helion",
      promotion: "Klasyka",
      new_price: 109.99,
      old_price: 149.99
    };
    this.data.push(potop);
    this.data.push(potop2);
    this.data.push(witcher);
    this.data.push(java);
  }

}
