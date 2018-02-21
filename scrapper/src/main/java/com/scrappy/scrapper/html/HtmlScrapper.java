package com.scrappy.scrapper.html;

import com.scrappy.scrapper.common.Book;

import java.util.List;


interface HtmlScrapper {
  
  /**
   * Scraps a bookstore collecting information required to build a
   * Book object and then returns a list of such objects.
   */
  List<Book> scrap();
}
