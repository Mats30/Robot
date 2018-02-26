package com.scrappy.scrapper.html.core.swiatksiazki;

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
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Scrapper used in swiatksiazki.pl bookstore to retrieve
 * data about promotions.
 *
 * @author Mateusz Tapa
 * @version 1.0-SNAPSHOT
 * @since 2018-02-21
 */
public class SwiatKsiazkiScrapper implements HtmlScrapper {

  private static final BookStore BOOKSTORE = BookStore.SWIAT_KSIAZKI;

  private static final String BASE_URL = "https://www.swiatksiazki.pl";

  private static final String DISCOUNTS_URL = "/swiat-niskich-cen";

  private static final String ISBN_REGEX = "\\d{13}";

  private static final Pattern ISBN_PATTERN = Pattern.compile(ISBN_REGEX);

    @Override
    public List<ScrappedBook> scrap() {
        List<ScrappedBook> scrappedBooks = new ArrayList<>();
        List<String> urlsToScrap = findPromotionUrls();
        urlsToScrap.forEach(url -> {
            try {
                final Document doc = Jsoup.connect(url).get();
                final String booksContainer = "product details product-item-details";
                final Elements elements = doc.getElementsByClass(booksContainer);
                elements.forEach(e -> {
                    try {
                        addScrappedBook(scrappedBooks, e, BookStore.SWIAT_KSIAZKI);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return scrappedBooks;
    }

    private void addScrappedBook(List<ScrappedBook> scrappedBooks, Element e, BookStore bookStore) throws InterruptedException {
        scrappedBooks.add(ScrappedBook.builder()
            .setAuthor(retrieveAuthorFrom(e))
            .setBookstore(bookStore)
            .setDiscountPrice(retrieveDiscountPriceFrom(e))
            .setListPrice(retrieveListPriceFrom(e))
            .setIsbn(retrieveIsbnFrom(e))
            .setTitle(retrieveTitleFrom(e))
            .setUrl(retrieveUrlFrom(e))
            .setGenre(retrieveGenreFrom(e))
            .build());
        Thread.sleep(1000);
    }

  List<ScrappedBook> scrapFromFile(File file) throws IOException {
    List<ScrappedBook> scrappedBooks = new ArrayList<>();
    final Document doc = Jsoup.parse(file, "UTF-8", "");
    final String booksContainer = "product details product-item-details";
    final Elements elements = doc.getElementsByClass(booksContainer);
    elements.forEach(e -> scrappedBooks.add(ScrappedBook.builder()
            .setAuthor(retrieveAuthorFrom(e))
            .setBookstore(BOOKSTORE)
            .setDiscountPrice(retrieveDiscountPriceFrom(e))
            .setListPrice(retrieveListPriceFrom(e))
            .setIsbn(retrieveIsbnFrom(e))
            .setTitle(retrieveTitleFrom(e))
            .setUrl(retrieveUrlFrom(e))
            .setGenre(retrieveGenreFrom(e))
            .build()));
    return scrappedBooks;
  }

  private String retrieveGenreFrom(Element element) {
    return retrieveGenreFrom(retrieveUrlFrom(element));
  }

  private String retrieveAuthorFrom(Element element) {
    final String authorContainer = "product author product-item-author";
    return element.getElementsByClass(authorContainer).text();
  }

  private String retrieveTitleFrom(Element element) {
    final String titleContainer = "product name product-item-name";
    return element.getElementsByClass(titleContainer).text();
  }

  private BigDecimal retrieveDiscountPriceFrom(Element element) {
    final String discountPriceContainer = "special-price";
    return retrievePriceFrom(element.getElementsByClass(discountPriceContainer).text());
  }

  private BigDecimal retrieveListPriceFrom(Element element) {
    final String listPriceContainer = "old-price";
    return retrievePriceFrom(element.getElementsByClass(listPriceContainer).text());
  }

  private String retrieveIsbnFrom(Element element) {
    final String isbnContainer = "product-item-link";
    return retrieveIsbnFrom(element.getElementsByClass(isbnContainer).attr("href"));
  }

  private String retrieveUrlFrom(Element element) {
    final String urlContainer = "product-item-link";
    return element.getElementsByClass(urlContainer).attr("href");
  }

  private BigDecimal retrievePriceFrom(String pattern) {
    if (pattern.isEmpty()) return BigDecimal.ZERO;
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

  private String retrieveGenreFrom(String url) {
    try {
      final Document doc = Jsoup.connect(url).get();
      final Elements genre = doc.getElementsMatchingOwnText("Książki /");
      return genre.get(0).text().replaceAll(" / ", ",").split(",")[1];
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }

  private List<String> findPromotionUrls() {
    List<String> links = new ArrayList<>();
    try {
      final Document doc = Jsoup.connect(BASE_URL + DISCOUNTS_URL).get();
      final String promotionUrlsContainer = "col-sm-6 col-md-5 no-padding hidden-xs";
      final Elements elements = doc.getElementsByClass(promotionUrlsContainer).tagName("a");
      elements.stream()
              .filter(Objects::nonNull)
              .forEach(e -> links.add(e.getElementsByTag("a").attr("href")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return links;
  }
}
