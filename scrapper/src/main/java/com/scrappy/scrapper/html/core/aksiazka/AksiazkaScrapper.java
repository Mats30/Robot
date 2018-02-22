package com.scrappy.scrapper.html.core.aksiazka;

import com.scrappy.scrapper.common.ScrappedBook;
import com.scrappy.scrapper.html.api.HtmlScrapper;
import com.scrappy.scrapper.html.model.BookStore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

public class AksiazkaScrapper implements HtmlScrapper {
  
  private static final String BASE_URL = "https://aksiazka.pl/";
  
  private static final String DISCOUNTS_URL = "wybrane-18,promocja-na-bestsellery/";
  
  private static final String BOOKSTORE = "Księgarnia aksiazka.pl";
  
  protected Document retrievePromoBooks() throws IOException {
    return Jsoup.connect(BASE_URL + DISCOUNTS_URL).get();
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
  public List<ScrappedBook> scrap() {
    try {
      final List<ScrappedBook> scrappedBooks = new ArrayList<>();
      final Document doc = this.retrievePromoBooks();
      final String booksContainer = "txtlistpoz";
      final Elements elements = doc.getElementsByClass(booksContainer);
      elements.forEach(e -> {
        final ScrappedBook scrappedBook = ScrappedBook.builder()
                              .setTitle(retrieveTitleFrom(e))
                              .setAuthor(retrieveAuthorFrom(e))
                              .setListPrice(retrieveListPriceFrom(e))
                              .setDiscountPrice(retrieveDiscountPriceFrom(e))
                              .setBookstore(BookStore.AKSIAZKA)
                              .setUrl(retrieveUrlFrom(e))
                              .setIsbn(retrieveIsbnFrom(retrieveUrlFrom(e)))
                              .build();
        scrappedBooks.add(scrappedBook);
      });
      return scrappedBooks;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return emptyList();
  }
  
  private String retrieveUrlFrom(Element element) {
    return BASE_URL + element.getElementsByTag("a").attr("href");
  }
  
  private BigDecimal retrieveDiscountPriceFrom(final Element element) {
    final String discountPriceContainer = ".cenabig";
    return retrievePriceFrom(element.select(discountPriceContainer).text());
  }
  
  private BigDecimal retrieveListPriceFrom(final Element element) {
    final String listPriceContainer = ".cenasmall";
    return retrievePriceFrom(element.select(listPriceContainer).text());
  }
  
  private String retrieveAuthorFrom(final Element element) {
    final String authorContainer = "txtpronagbfullopo-aut";
    return element.getElementsByClass(authorContainer).text();
  }
  
  private String retrieveTitleFrom(final Element element) {
    final String titleContainer = "txtpronagbfullopo-tyt";
    return element.getElementsByClass(titleContainer).text();
  }
  
  private BigDecimal retrievePriceFrom(String pattern) {
    String price = pattern.split(" ")[0];
    return BigDecimal.valueOf(Double.parseDouble(price));
  }
  
  private String retrieveIsbnFrom(String url) {
    try {
      final Document doc = this.retrieveSinglePromoBook(url);
      final String isbnOuterContainer = "txttresc";
      final Elements product = doc.getElementsByClass(isbnOuterContainer);
      final Elements isbn = product.select("div:containsOwn(isbn)");
      final String isbnInnerContainer = ".boldi";
      return isbn.select(isbnInnerContainer).text();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }
}
