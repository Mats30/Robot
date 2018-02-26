package com.scrappy.scrapper.html.core.pwn;

import com.scrappy.scrapper.common.ScrappedBook;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Test
public class PwnScrapperTest {
  private final File file = new File("src/test/resources/pwn/pwn.html");
  private final PwnScrapper scrapper = new PwnScrapper();
  private final List<ScrappedBook> books = scrapper.scrapFromFile(file);

  public PwnScrapperTest() throws IOException {
  }

  public void retrieveAuthorFrom() {
    //given
    String expected = "Janusz Christa";
    //when
    String actual = books.get(0).author();
    //then
    assertEquals(actual, expected);
  }

  public void retrieveTitleFrom() {
    //given
    String expected = "Kajko i Kokosz Szkoła latania";
    //when
    String actual = books.get(0).title();
    //then
    assertEquals(actual, expected);
  }

  public void retrieveListPrice() {
    //given
    BigDecimal expected = BigDecimal.valueOf(19.99);
    //when
    BigDecimal actual = books.get(0).listPrice();
    //then
    assertEquals(actual, expected);
  }

  public void retrieveDiscountPrice() {
    //given
    BigDecimal expected = BigDecimal.valueOf(14.59);
    //when
    BigDecimal actual = books.get(0).discountPrice();
    //then
    assertEquals(actual, expected);
  }

  public void retrieveUrl() throws IOException {
    //given
    String expected = "https://ksiegarnia.pwn.pl/Kajko-i-Kokosz-Szkola-latania,68551781,p.html";
    //when
    String actual = books.get(0).url();
    //then
    assertEquals(actual, expected);
  }

  public void retrieveIsbn() {
    //given
    String expected = "9788323746935";
    //when
    String actual = books.get(0).isbn();
    //then
    assertEquals(actual, expected);
  }

  public void retrieveGenre() {
    //given
    String expected = "Literatura piękna";
    //when
    String actual = books.get(0).genre();
    //then
    assertEquals(actual, expected);

  }
}
