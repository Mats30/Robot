import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs/Observable";
import {BooksApi} from "../entities/booksApi";
import {isUndefined} from "util";

@Injectable()
export class BookService {
  private host: string = "http://localhost:8080/database-1.0-SNAPSHOT/books";

  constructor(private http: HttpClient) {
  }

  public getPagedBooksList(page: number, numberInPage: number, sort: string): Observable<BooksApi> {
    if (isUndefined(numberInPage) || isUndefined(sort)) {
      numberInPage = 5;
      sort = "title";
    }

    const requestUrlParams =
      `?page=${page}&size=${numberInPage}&sort=${sort}`;

    return this.http.get<BooksApi>(this.host + "/all" + requestUrlParams);
  }

}
