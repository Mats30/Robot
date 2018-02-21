import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {MaterialModule} from "./modules/material.module";
import {BooksListComponent} from './components/books-list/books-list.component';
import {RouterModule} from "@angular/router";
import {BookService} from "./services/book.service";
import {UtilityService} from "./services/utility.service";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

@NgModule({
  declarations: [
    AppComponent,
    BooksListComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MaterialModule,
    RouterModule
  ],
  providers: [UtilityService, BookService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
