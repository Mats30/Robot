package com.scrappy.scrapper.html;

import com.scrappy.scrapper.common.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Test
public class HtmlScrapperTest {

  public void temp() {
    //given
    final HtmlScrapper htmlScrapper = new NiedzielaScrapper();
    Book.builder()
        .setAuthor("Marcin")
        .setBookstore("Lambdy")
        .setDiscountPrice(BigDecimal.valueOf(1231.3123))
        .setListPrice(BigDecimal.valueOf(241.43))
        .setIsbn("3424-fsdf")
        .setTitle("Smieje sie zawsze")
        .setUrl("ddd")
        .build();
    //when
    final Book book = this.produceBook();
    final List<Book> books = htmlScrapper.scrap();
    //then
    assertThat(books).contains(book);
  }
  
  private Book produceBook() {
       Book.builder()
             .setAuthor("Abp Stanisław Nowak")
             .setBookstore("Lambdy")
             .setDiscountPrice(BigDecimal.valueOf(12.00))
             .setListPrice(BigDecimal.valueOf(16.00))
             .setIsbn("978-83-62515-16-5")
             .setTitle("Krzyż stał się nam bramą")
             .setUrl("https://ksiegarnia.niedziela.pl/site/ksiazka/365/")
             .build();
    return null;
  }
  
  public void temp2() throws IOException {
    File input = new File("/home/sandor/IdeaProjects/Robot/scrapper/src/test/resources/com.scrappy.scrapper.html/promocje.html");
    final Document result = Jsoup.parse(input, "UTF-8", "");
    final Elements title = result.getElementsByClass("polecamy");
    //System.out.println(title.first());
    System.out.println("-----------------------------------");
    System.out.println(title.get(0));
  
  }
  
  
  
}