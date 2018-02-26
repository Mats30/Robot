package com.scrappy.scrapper.html.core.swiatksiazki;

import com.scrappy.scrapper.common.ScrappedBook;
import com.scrappy.scrapper.html.model.BookStore;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

@Test
public class SwiatKsiazkiScrapperTest {
  private final File input = new File("src/test/resources/swiatksiazki/swiatksiazki.html");
  private final SwiatKsiazkiScrapper scrapper = new SwiatKsiazkiScrapper();
  private final List<ScrappedBook> books = scrapper.scrapFromFile(input);

  public SwiatKsiazkiScrapperTest() throws IOException {
  }

  public void retrieveAuthor() throws IOException {
    //given
    String expected = "Bente Pedersen";
    //when
    String actual = books.get(0).author();
    //then
    assertEquals(expected, actual);
  }

  public void retrieveTitle() throws IOException {
    //given
    String expected = "Raija. Saga ze śnieżnej krainy. Tom 22. Mężczyzna ...";
    //when
    String actual = books.get(0).title();
    //then
    assertEquals(expected, actual);
  }

  public void retrieveDiscountPrice() throws IOException {
    //given
    BigDecimal expected = BigDecimal.valueOf(1.99);
    //when
    BigDecimal actual = books.get(0).discountPrice();
    //then
    assertEquals(expected, actual);
  }

  public void retrieveListPrice() throws IOException {
    //given
    BigDecimal expected = BigDecimal.valueOf(9.90);
    //when
    BigDecimal actual = books.get(0).listPrice();
    //then
    assertEquals(expected, actual);
  }

  public void retrieveBookstoreName() throws IOException {
    //given
    BookStore expected = BookStore.SWIAT_KSIAZKI;
    //when
    BookStore actual = books.get(0).bookstore();
    //then
    assertEquals(expected, actual);
  }

  public void retrieveIsbn() throws IOException {
    //given
    String expected = "9788375583946";
    //when
    String actual = books.get(0).isbn();
    //then
    assertEquals(expected, actual);
  }

  public void retrieveUrl() throws IOException {
    //given
    String expected = "https://www.swiatksiazki.pl/raija-saga-ze-snieznej-krainy-tom-22-mezczyzna-w-masce-1682304-ksiazka.html";
    //when
    String actual = books.get(0).url();
    //then
    assertEquals(expected, actual);
  }

  public void retrieveBookGenre() {
    //given
    String expected = "proza";
    //when
    String actual = books.get(0).genre();
    //then
    assertEquals(actual, expected);
  }
}