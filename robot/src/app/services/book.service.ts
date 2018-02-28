import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs/Observable";
import {BooksApi} from "../entities/booksApi";
import {isUndefined} from "util";

@Injectable()
export class BookService {
  private host: string = "http://147.135.210.145:8080/database-1.0-RC/books";

  constructor(private http: HttpClient) {
  }

  public getPagedBooksList(page: number, numberInPage: number, sort: string, direction: string): Observable<BooksApi> {
    if (isUndefined(numberInPage) || isUndefined(sort)) {
      numberInPage = 5;
      sort = "title";
      direction = "asc";
    }

    const requestUrlParams =
      `?page=${page}&size=${numberInPage}&sort=${sort},${direction}`;

    return this.http.get<BooksApi>(this.host + "/all" + requestUrlParams);
  }

}
