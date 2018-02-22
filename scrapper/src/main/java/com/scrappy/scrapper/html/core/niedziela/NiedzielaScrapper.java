package com.scrappy.scrapper.html.core.niedziela;

import com.scrappy.scrapper.common.Book;
import com.scrappy.scrapper.html.api.HtmlScrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.emptyList;

public class NiedzielaScrapper implements HtmlScrapper {
  
  private static final String BASE_URL = "https://ksiegarnia.niedziela.pl/";
  
  private static final String DISCOUNTS_URL = "site/promocje/";
  
  private static final String BOOKSTORE = "Księgarnia Niedziela";
  
  private static final String ISBN_REGEX = "\\d*-\\d*-\\d*-\\d*";
  
  private static final Pattern ISBN_PATTERN = Pattern.compile(ISBN_REGEX);
  
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
      final String booksContainer = "polecamy";
      final Elements elements = doc.getElementsByClass(booksContainer);
      elements.forEach(e -> {
        final Book book = Book.builder()
                              .setTitle(retrieveTitleFrom(e))
                              .setAuthor(retrieveAuthorFrom(e))
                              .setListPrice(retrieveListPriceFrom(e))
                              .setDiscountPrice(retrieveDiscountPriceFrom(e))
                              .setBookstore(BOOKSTORE)
                              .setUrl(retrieveUrlFrom(e))
                              .setIsbn(retrieveIsbnFrom(retrieveUrlFrom(e)))
                              .build();
        books.add(book);
      });
      return books;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return emptyList();
  }
  
  protected Document retrievePromoBooks() throws IOException {
    return Jsoup.connect(BASE_URL + DISCOUNTS_URL).get();
  }
  
  protected Document retrieveSinglePromoBook(String url) throws IOException {
    return Jsoup.connect(url).get();
  }
  
  private String retrieveAuthorFrom(final Element element) {
    final String authorContainer = "polecamy_dzial";
    return element.getElementsByClass(authorContainer).text();
  }
  
  private String retrieveTitleFrom(final Element element) {
    final String titleContainer = "polecamy_tytul";
    return element.getElementsByClass(titleContainer).text();
  }
  
  private String retrieveIsbnFrom(String url) {
    try {
      final Document doc = this.retrieveSinglePromoBook(url);
      final Elements elements = doc.getElementsMatchingOwnText(ISBN_REGEX);
      final String result = elements.first().toString();
      final Matcher matcher = ISBN_PATTERN.matcher(result);
      matcher.find();
      return matcher.group();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }
  
  private String retrieveUrlFrom(Element element) {
    return BASE_URL + element.getElementsByTag("a").attr("href");
  }
  
  private BigDecimal retrieveListPriceFrom(Element element) {
    final String listPriceContainer = "strike";
    String pattern = element.getElementsByTag(listPriceContainer).text();
    return retrievePriceFrom(pattern);
  }
  
  private BigDecimal retrieveDiscountPriceFrom(Element element) {
    final String discountPriceContainer = "polecamy_cena";
    String pattern = element.getElementsByClass(discountPriceContainer).tagName("span").get(0).text();
    return retrievePriceFrom(pattern);
  }
  
  private BigDecimal retrievePriceFrom(String pattern) {
    String price = pattern.split(" ")[0];
    price = price.replaceAll(",", ".");
    return BigDecimal.valueOf(Double.parseDouble(price));
  }
}
