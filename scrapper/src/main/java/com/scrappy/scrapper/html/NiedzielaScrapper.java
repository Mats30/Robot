package com.scrappy.scrapper.html;

import com.scrappy.scrapper.common.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.emptyList;

class NiedzielaScrapper implements HtmlScrapper {
  
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
      elements.forEach(element -> {
        String bookUrl = "https://ksiegarnia.niedziela.pl/" + element.getElementsByTag("a").attr("href");
        final Book book = Book.builder()
                        .setTitle(element.getElementsByClass("polecamy_tytul").text())
                        .setAuthor(element.getElementsByClass("polecamy_dzial").text())
                        .setListPrice(retrievePriceFrom(element.getElementsByTag("strike").text()))
                        .setDiscountPrice(retrievePriceFrom(element.getElementsByClass("polecamy_cena").tagName("span").get(0).text()))
                        .setBookstore("Księgarnia Niedziela")
                        .setUrl(bookUrl)
                        .setIsbn(retrieveIsbnFrom(bookUrl)).build();
        books.add(book);
      });
      return books;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return emptyList();
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
  
  private BigDecimal retrievePriceFrom(String pattern) {
    String price = pattern.split(" ")[0];
    price = price.replaceAll(",", ".");
    return BigDecimal.valueOf(Double.parseDouble(price));
  }
}
