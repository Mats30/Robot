package com.scrappy.scrapper.html.core.czytampl;

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

/**
 * Scrapper used in czytam.pl bookstore to retrieve
 * data about promotions.
 *
 * @author Mateusz Tapa
 * @version 1.0-RC
 * @since 2018-02-21
 */
public class CzytamplScrapper implements HtmlScrapper {

  private static final String BASE_URL = "https://czytam.pl";

  private static final String DISCOUNTS_URL = "/promocje,1.html";

  private static final BookStore BOOKSTORE = BookStore.CZYTAM;

  private static final String ISBN_REGEX = "\\d{13}";

  private static final Pattern ISBN_PATTERN = Pattern.compile(ISBN_REGEX);

    @Override
    public List<ScrappedBook> scrap() {
        List<ScrappedBook> scrappedBooks = new ArrayList<>();
        List<String> urlsToScrap = findPromotionUrls();

        urlsToScrap.forEach(url -> {
            try {
                final Document doc = Jsoup.connect(url).get();
                final String booksContainer = "col-small-info";
                final Elements elements = doc.getElementsByClass(booksContainer);
                elements.forEach(e -> {
                    try {
                        addScrappedBook(scrappedBooks, e);
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

    private void addScrappedBook(List<ScrappedBook> scrappedBooks, Element e) throws InterruptedException {
        scrappedBooks.add(ScrappedBook.builder()
            .setAuthor(retrieveAuthorFrom(e))
            .setBookstore(BOOKSTORE)
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
    final Elements elements = doc.getElementsByClass("col-small-info");
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

  private String retrieveIsbnFrom(Element element) {
    return retrieveIsbnFrom(retrieveUrlFrom(element));
  }

  private String retrieveUrlFrom(Element element) {
    return BASE_URL + element.getElementsByTag("a").attr("href");
  }

  private BigDecimal retrieveDiscountPriceFrom(Element element) {
    final String discountPriceContainer = "product-price";
    return retrieveListPriceFrom(element.getElementsByClass(discountPriceContainer).text(), true);
  }

  private BigDecimal retrieveListPriceFrom(Element element) {
    final String listPriceContainer = "product-price";
    return retrieveListPriceFrom(element.getElementsByClass(listPriceContainer).text(), false);
  }

  private String retrieveTitleFrom(Element element) {
    final String titleContainer = "product-title";
    return element.getElementsByClass(titleContainer).text();
  }

  private String retrieveAuthorFrom(Element element) {
    final String authorContainer = "product-author";
    return element.getElementsByClass(authorContainer).text();
  }

  private List<String> findPromotionUrls() {
    List<String> links = new ArrayList<>();
    try {
      final Document doc = Jsoup.connect(BASE_URL + DISCOUNTS_URL).get();
      final String latestPageNumberContainer = "show-for-medium-up";
      final String text = doc.getElementsByClass(latestPageNumberContainer).tagName("a").text().split(" ")[21];
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
      final Elements genre = doc.getElementsByClass("current").tagName("a");
      return genre.get(1).text();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }
}
