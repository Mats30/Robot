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
  @Override
  public List<Book> scrap() {
    try {
      List<Book> books = new ArrayList<>();
      final Document doc = Jsoup.connect("https://ksiegarnia.niedziela.pl/site/promocje/").get();
      final Elements elements = doc.getElementsByClass("polecamy");
      elements.forEach((e) -> {
        String bookUrl = "https://ksiegarnia.niedziela.pl/" + e.getElementsByTag("a").attr("href");
        Book book = Book.builder().setTitle(e.getElementsByClass("polecamy_tytul").text())
                        .setAuthor(e.getElementsByClass("polecamy_dzial").text()).setListPrice
                                                                                      (retrievePriceFrom(e.getElementsByTag("strike").text())).setDiscountPrice(retrievePriceFrom(e.getElementsByClass("polecamy_cena").tagName("span").get(0).text())).setBookstore("Księgarnia Niedziela").setUrl(bookUrl).setIsbn(retrieveIsbnFrom(bookUrl)).build();
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
      final Document doc = Jsoup.connect(url).get();
      final String regex = "\\d*-\\d*-\\d*-\\d*";
      final Elements elements = doc.getElementsMatchingOwnText(regex);
      String result = elements.get(0).toString();
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(result);
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
