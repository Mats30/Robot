package com.scrappy.scrapper.html.api;

import com.scrappy.scrapper.common.ScrappedBook;

import java.util.List;

public interface HtmlScrapper {

  /**
   * Scraps a bookstore collecting information required to build a
   * ScrappedBook object and then returns a list of such objects.
   */
  List<ScrappedBook> scrap();
}
