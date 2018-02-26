package com.scrappy.scrapper.html.core.pwn;

import com.scrappy.scrapper.common.ScrappedBook;
import com.scrappy.scrapper.html.api.HtmlScrapper;
import com.scrappy.scrapper.html.model.BookStore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.emptyList;

public class PwnScrapper implements HtmlScrapper {
  private static final String BASE_URL = "https://ksiegarnia.pwn.pl";

  private static final String DISCOUNTS_URL = "/promocje";

  private static final BookStore BOOKSTORE = BookStore.PWN;

  private static final String ISBN_REGEX = "\\d{13}";

  private static final Pattern ISBN_PATTERN = Pattern.compile(ISBN_REGEX);


  @Override
  public List<ScrappedBook> scrap() {
    List<ScrappedBook> scrappedBooks = new ArrayList<>();
    try {
      final Document doc = Jsoup.connect(BASE_URL + DISCOUNTS_URL).get();
      final Elements elements = doc.getElementsByClass("emp-product-hover");
      elements.forEach(e -> {
        try {
          addScrappedBook(scrappedBooks, e);
        } catch (InterruptedException e1) {
          e1.printStackTrace();
        }
      });
      return scrappedBooks;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return emptyList();
  }

  private void addScrappedBook(List<ScrappedBook> scrappedBooks, Element e) throws InterruptedException {
    scrappedBooks.add(ScrappedBook.builder()
        .setAuthor(retrieveAuthorFrom(e))
        .setBookstore(BOOKSTORE)
        .setDiscountPrice(retrieveDiscountPriceFrom(e))
        .setListPrice(retrieveListPriceFrom(e))
        .setIsbn(retrieveIsbnFrom(retrieveUrlFrom(e)))
        .setTitle(retrieveTitleFrom(e))
        .setUrl(retrieveUrlFrom(e))
        .setGenre(retrieveGenreFrom(e))
        .build());
    Thread.sleep(1000);
  }

  List<ScrappedBook> scrapFromFile(File file) throws IOException {
    List<ScrappedBook> scrappedBooks = new ArrayList<>();
    final Document doc = Jsoup.parse(file, "UTF-8", "");
    final Elements elements = doc.getElementsByClass("emp-product-hover");
    elements.forEach(e -> scrappedBooks.add(ScrappedBook.builder()
            .setAuthor(retrieveAuthorFrom(e))
            .setTitle(retrieveTitleFrom(e))
            .setListPrice(retrieveListPriceFrom(e))
            .setDiscountPrice(retrieveDiscountPriceFrom(e))
            .setUrl(retrieveUrlFrom(e))
            .setIsbn(retrieveIsbnFrom(e))
            .setBookstore(BOOKSTORE)
            .setGenre(retrieveGenreFrom(e))
            .build()));
    return scrappedBooks;
  }

  private String retrieveGenreFrom(Element element) {
    return retrieveGenreFrom(retrieveUrlFrom(element));
  }

  private String retrieveGenreFrom(String url) {
    try {
      final Document doc = Jsoup.connect(url).get();
      final Elements genre = doc.getElementsByAttribute("itemprop");
      return genre.get(5).text();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }

  private String retrieveIsbnFrom(Element element) {
    return retrieveIsbnFrom(retrieveUrlFrom(element));
  }

  private String retrieveUrlFrom(Element element) {
    return BASE_URL + element.select("a").attr("href");
  }

  private BigDecimal retrieveDiscountPriceFrom(Element element) {
    return parseStringPriceToBigDecimal(element.getElementsByClass("emp-sale-price-value").text());
  }

  private BigDecimal retrieveListPriceFrom(Element element) {
    return parseStringPriceToBigDecimal(element.getElementsByClass("emp-base-price").text());
  }

  private String retrieveTitleFrom(Element element) {
    return element.getElementsByClass("emp-info-title")
            .first()
            .text();
  }

  private String retrieveAuthorFrom(Element element) {
    return element.getElementsByClass("emp-info-author")
            .first()
            .text();
  }

  private BigDecimal parseStringPriceToBigDecimal(String pattern) {
    if (pattern.isEmpty()) {
      return BigDecimal.ZERO;
    }
    String price = pattern.split(" ")[0].replaceAll(",", ".");
    return BigDecimal.valueOf(Double.parseDouble(price));
  }

  private String retrieveIsbnFrom(String url) {
    try {
      final Document doc = Jsoup.connect(url).get();
      final Elements elements = doc.getElementsMatchingOwnText(ISBN_REGEX);
      String result = elements.get(0).toString();
      Matcher matcher = ISBN_PATTERN.matcher(result);
      matcher.find();
      return matcher.group();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }
}
