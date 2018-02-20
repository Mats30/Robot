package com.scrappy.scrapper.html;

import com.scrappy.scrapper.common.Book;

import java.util.List;

interface HtmlScrapper {
  List<Book> scrap();
}
