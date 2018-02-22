import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from "rxjs/Observable";
import {BooksApi} from "../entities/booksApi";

@Injectable()
export class BookService {
  private host: string = "http://localhost:8080/database-1.0-SNAPSHOT/books";

  constructor(private http: HttpClient) {
  }

  public getBooksList(url: string): Observable<BooksApi> {
    return this.http.get<BooksApi>(this.host + url);
  }

  public getPagedBooksList(sort: string, order: string, page: number, numberInPage: number): Observable<BooksApi> {
    const requestUrlParams =
      `?sort=${sort}&order=${order}&page=${page + 1}&num=${numberInPage}`;

    return this.http.get<BooksApi>(this.host + "/test" + requestUrlParams);
  }

}


