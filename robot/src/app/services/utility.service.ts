import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class UtilityService {

  private host: string = "/api";

  constructor(private http: HttpClient) {
  }

  public get(url: string): Observable<any> {
    return this.http.get(this.host + url);
  }

}
