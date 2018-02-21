import {Injectable} from '@angular/core';
import {UtilityService} from "./utility.service";
import {Book} from "../entities/book";

@Injectable()
export class BookService {
  data : Array<Book>;

  constructor(private utilityService: UtilityService) {
  }

  public getBooksList() {
    return this.utilityService.get('/all').subscribe(
      data => this.data,
      error => console.log(error),
      () => console.log("Books succesfully downloaded")
    );
  }

}
