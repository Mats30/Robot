package com.scrappy.scrapper.html.core.czytampl;

import com.scrappy.scrapper.common.Book;
import com.scrappy.scrapper.html.api.HtmlScrapper;
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

/**
 * Scrapper used in czytam.pl bookstore to retrieve
 * data about promotions.
 *
 * @author Mateusz Tapa
 * @version 1.0-SNAPSHOT
 * @since 2018-02-21
 */
public class CzytamplScrapper implements HtmlScrapper {
  
  private static final String BASE_URL = "https://czytam.pl";
  
  private static final String DISCOUNTS_URL = "/promocje,1.html";
  
  private static final String BOOKSTORE = "Czytam.pl";
  
  @Override
  public List<Book> scrap() {
    List<Book> books = new ArrayList<>();
    List<String> urlsToScrap = findPromotionUrls();
    
    urlsToScrap.forEach(url -> {
      try {
        final Document doc = Jsoup.connect(url).get();
        final Elements elements = doc.getElementsByClass("col-small-info");
        elements.forEach(e -> books
                                  .add(Book.builder()
                                  .setAuthor(retrieveAuthorFrom(e))
                                  .setBookstore(BOOKSTORE)
                                  .setDiscountPrice(retrieveDiscountPriceFrom(e))
                                  .setListPrice(retrieveListPriceFrom(e))
                                  .setIsbn(retrieveIsbnFrom(e))
                                  .setTitle(retrieveTitleFrom(e))
                                  .setUrl(retrieveUrlFrom(e))
                                  .build()));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    return books;
  }
  
  List<Book> scrapFromFile(File file) throws IOException {
    List<Book> books = new ArrayList<>();
    final Document doc = Jsoup.parse(file, "UTF-8", "");
    final Elements elements = doc.getElementsByClass("col-small-info");
    elements.forEach(e -> books.add(Book.builder().setAuthor(retrieveAuthorFrom(e)).setTitle
                                                                                        (retrieveTitleFrom(e)).setListPrice(retrieveListPriceFrom(e)).setDiscountPrice(retrieveDiscountPriceFrom(e)).setUrl(retrieveUrlFrom(e)).setIsbn(retrieveIsbnFrom(e)).setBookstore(BOOKSTORE).build()));
    return books;
  }
  
  private String retrieveIsbnFrom(Element element) {
    return retrieveIsbnFrom(retrieveUrlFrom(element));
  }
  
  private String retrieveUrlFrom(Element element) {
    return BASE_URL + element.getElementsByTag("a").attr("href");
  }
  
  private BigDecimal retrieveDiscountPriceFrom(Element element) {
    return retrieveListPriceFrom(element.getElementsByClass("product-price").text(), true);
  }
  
  private BigDecimal retrieveListPriceFrom(Element element) {
    return retrieveListPriceFrom(element.getElementsByClass("product-price").text(), false);
  }
  
  private String retrieveTitleFrom(Element element) {
    return element.getElementsByClass("product-title").text();
  }
  
  private String retrieveAuthorFrom(Element element) {
    return element.getElementsByClass("product-author").text();
  }
  
  private List<String> findPromotionUrls() {
    List<String> links = new ArrayList<>();
    try {
      final Document doc = Jsoup.connect(BASE_URL + DISCOUNTS_URL).get();
      final String text = doc.getElementsByClass("show-for-medium-up").tagName("a").text().split
                                                                                               (" ")[21];
      final int pagesCount = Integer.parseInt(text);
      
      final String discountsUrlLeft = DISCOUNTS_URL.substring(0, 10);
      
      final String discountsUrlRight = DISCOUNTS_URL.substring(11, 16);
      
      for (int i = 1; i <= pagesCount; i++) {
        links.add(BASE_URL + discountsUrlLeft + i + discountsUrlRight);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return links;
  }
  
  private BigDecimal retrieveListPriceFrom(String pattern, boolean discount) {
    String price;
    if (pattern.isEmpty()) {
      return BigDecimal.ZERO;
    }
    if (discount) {
      price = pattern.split(" ")[2].replaceAll(",", ".");
    } else {
      price = pattern.split(" ")[0].replaceAll(",", ".");
    }
    return BigDecimal.valueOf(Double.parseDouble(price));
  }
  
  private String retrieveIsbnFrom(String url) {
    try {
      final Document doc = Jsoup.connect(url).get();
      final String regex = "\\d{13}";
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
}
