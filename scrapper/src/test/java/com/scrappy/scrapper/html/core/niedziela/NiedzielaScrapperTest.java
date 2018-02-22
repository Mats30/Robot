package com.scrappy.scrapper.html.core.niedziela;

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
public class NiedzielaScrapperTest {
  
  public void bookIsScrapped() {
    //given
    final HtmlScrapper htmlScrapper = new FileNiedzielaScrapper();
    //when
    final Book book = Book.builder()
                          .setAuthor("Włodzimierz Fijałkowski")
                          .setBookstore("Księgarnia Niedziela")
                          .setDiscountPrice(BigDecimal.valueOf(15.0))
                          .setListPrice(BigDecimal.valueOf(29.9))
                          .setIsbn("978-83-62515-31")
                          .setTitle("Jestem od poczęcia. Pamiętnik dziecka w pierwszej fazie życia")
                          .setUrl("https://ksiegarnia.niedziela.pl//site/ksiazka/377/")
                          .build();
    final List<Book> books = htmlScrapper.scrap();
    //then
    assertThat(books).contains(book);
  }
}

class FileNiedzielaScrapper extends NiedzielaScrapper {
  @Override
  protected Document retrievePromoBooks() throws IOException {
    File input = new File("src/test/resources/niedziela/discounts.html");
    return Jsoup.parse(input, "UTF-8", "");
  }
  
  @Override
  protected Document retrieveSinglePromoBook(String url) throws IOException {
    File input = new File("src/test/resources/niedziela/product.html");
    return Jsoup.parse(input, "UTF-8", "");
  }
}