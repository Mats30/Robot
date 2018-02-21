package com.scrappy.scrapper.html;

import com.scrappy.scrapper.common.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

class AksiazkaScrapper implements HtmlScrapper {
  
  protected Document retrievePromoBooks() throws IOException {
    return Jsoup.connect("https://aksiazka.pl/wybrane-18,promocja-na-bestsellery/").get();
  }
  
  protected Document retrieveSinglePromoBook(String url) throws IOException {
    return Jsoup.connect(url).get();
  }
  
  /**
   * Iterates through a container on a single page with discounted books
   * collecting information about them.
   * ISBNs are scrapped from a page of each book, while the rest is
   * scrapped from the main page alone.
   *
   * @return list of discounted books.
   */
  @Override
  public List<Book> scrap() {
    try {
      final List<Book> books = new ArrayList<>();
      final Document doc = this.retrievePromoBooks();
      final Elements elements = doc.getElementsByClass("txtlistpoz");
      elements.forEach(element -> {
        String bookUrl = "https://aksiazka.pl/" + element.getElementsByTag("a").attr("href");
        final Book book = Book.builder()
                              .setTitle(element.getElementsByClass("txtpronagbfullopo-tyt").text())
                              .setAuthor(element.getElementsByClass("txtpronagbfullopo-aut").text())
                              .setListPrice(retrievePriceFrom(element.select(".cenasmall").text()))
                              .setDiscountPrice(retrievePriceFrom(element.select(".cenabig").text()))
                              .setBookstore("Księgarnia aksiazka.pl")
                              .setUrl(bookUrl)
                              .setIsbn(retrieveIsbnFrom(bookUrl))
                              .build();
        books.add(book);
      });
      return books;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return emptyList();
  }
  
  private BigDecimal retrievePriceFrom(String pattern) {
    String price = pattern.split(" ")[0];
    return BigDecimal.valueOf(Double.parseDouble(price));
  }
  
  private String retrieveIsbnFrom(String url) {
    try {
      final Document doc = this.retrieveSinglePromoBook(url);
      final Elements product = doc.getElementsByClass("txttresc");
      final Elements isbn = product.select("div:containsOwn(isbn)");
      return isbn.select(".boldi").text();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }
}
