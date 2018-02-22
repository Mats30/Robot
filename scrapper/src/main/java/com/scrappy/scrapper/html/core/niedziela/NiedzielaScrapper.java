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

class NiedzielaScrapper implements HtmlScrapper {
  
  private static final String BASE_URL = "https://ksiegarnia.niedziela.pl/";
  
  private static final String BOOKSTORE = "Księgarnia Niedziela";
  
  protected Document retrievePromoBooks() throws IOException {
    return Jsoup.connect("https://ksiegarnia.niedziela.pl/site/promocje/").get();
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
      final Elements elements = doc.getElementsByClass("polecamy");
      elements.forEach(e -> {
        final Book book = Book.builder()
                        .setTitle(retrieveTitleFrom(e))
                        .setAuthor(retrieveAuthorFrom(e))
                        .setListPrice(retrieveListPriceFrom(e))
                        .setDiscountPrice(retrieveDiscountPriceFrom(e))
                        .setBookstore(BOOKSTORE)
                        .setUrl(retrieveUrlFrom(e))
                        .setIsbn(retrieveIsbnFrom(retrieveUrlFrom(e))).build();
        books.add(book);
      });
      return books;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return emptyList();
  }
  
  private String retrieveAuthorFrom(final Element element) {
    return element.getElementsByClass("polecamy_dzial").text();
  }
  
  private String retrieveTitleFrom(final Element element) {
    return element.getElementsByClass("polecamy_tytul").text();
  }
  
  private String retrieveIsbnFrom(String url) {
    try {
      final Document doc = this.retrieveSinglePromoBook(url);
      final String regex = "\\d*-\\d*-\\d*-\\d*";
      final Elements elements = doc.getElementsMatchingOwnText(regex);
      final String result = elements.first().toString();
      final Pattern pattern = Pattern.compile(regex);
      final Matcher matcher = pattern.matcher(result);
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
    String pattern = element.getElementsByTag("strike").text();
    return retrievePriceFrom(pattern);
  }
  
  private BigDecimal retrieveDiscountPriceFrom(Element element) {
    String pattern = element.getElementsByClass("polecamy_cena").tagName("span").get(0).text();
    return retrievePriceFrom(pattern);
  }
  
  private BigDecimal retrievePriceFrom(String pattern) {
    String price = pattern.split(" ")[0];
    price = price.replaceAll(",", ".");
    return BigDecimal.valueOf(Double.parseDouble(price));
  }
}
