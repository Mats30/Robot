package com.scrappy.scrapper.html.core.aksiazka;

import com.scrappy.scrapper.common.Book;
import com.scrappy.scrapper.html.api.HtmlScrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Test
public class AksiazkaScrapperTest {
  
  public void bookIsScrapped() {
    //given
    final HtmlScrapper htmlScrapper = new FileAksiazkaScrapper();
    //when
    final Book book = Book.builder()
                          .setAuthor("Bernadette McDonald, Maciej Krupa")
                          .setBookstore("Księgarnia aksiazka.pl")
                          .setDiscountPrice(BigDecimal.valueOf(29.8))
                          .setListPrice(BigDecimal.valueOf(44.99))
                          .setIsbn("9788326826092")
                          .setTitle("Kurtyka. Sztuka wolności")
                          .setUrl("https://aksiazka.pl/produkt-374296,kurtyka-sztuka-wolnosci")
                          .build();
    final List<Book> books = htmlScrapper.scrap();
    //then
    assertThat(books).contains(book);
  }
  
}

class FileAksiazkaScrapper extends AksiazkaScrapper {
  @Override
  protected Document retrievePromoBooks() throws IOException {
    File input = new File("src/test/resources/aksiazka/discounts.html");
    return Jsoup.parse(input, "UTF-8", "");
  }
  
  @Override
  protected Document retrieveSinglePromoBook(String url) throws IOException {
    File input = new File("src/test/resources/aksiazka/product.html");
    return Jsoup.parse(input, "UTF-8", "");
  }
}