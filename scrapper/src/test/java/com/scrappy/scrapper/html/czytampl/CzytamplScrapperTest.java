package com.scrappy.scrapper.html.czytampl;

import com.scrappy.scrapper.common.Book;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Test
public class CzytamplScrapperTest {
    private final File file = new File("src/test/resources/czytampl/czytampl.html");
    private final CzytamplScrapper scrapper = new CzytamplScrapper();
    private final List<Book> books = scrapper.scrapFromFile(file);

    public CzytamplScrapperTest() throws IOException {
    }

    public void retrieveAuthor() throws IOException {
        //given
        String expected = "Grzegorz Kubicki, Maciej Drzewicki";
        //when
        String actual = books.get(0).author();
        //then
        assertEquals(actual,expected);
    }

    public void retrieveTitle() throws IOException {
        //given
        String expected = "Ania. Biografia Anny Przybylskiej";
        //when
        String actual = books.get(0).title();
        //then
        assertEquals(actual, expected);
    }

    public void retrieveListPrice() throws IOException {
        //given
        BigDecimal expected = BigDecimal.valueOf(54.92);
        //when
        BigDecimal actual = books.get(1).listPrice();
        //then
        assertEquals(actual, expected);
    }

    public void retrieveDiscountPrice() throws IOException {
        //given
        BigDecimal expected = BigDecimal.valueOf(31.98);
        //when
        BigDecimal actual = books.get(1).discountPrice();
        //then
        assertEquals(actual, expected);
    }

    public void retrieveUrl() throws IOException{
        //given
        String expected = "https://czytam.pl/k,ks_683135,Ania-Biografia-Anny-Przybylskiej-Grzegorz-Kubicki-Maciej-Drzewicki.html";
        //when
        String actual = books.get(0).url();
        //then
        assertEquals(actual,expected);
    }

    public void retrieveIsbn() throws IOException{
        //given
        String expected = "9788326825828";
        //when
        String actual = books.get(0).isbn();
        //then
        assertEquals(actual,expected);
    }

    public void retrieveBookstoreName() throws IOException {
        //given
        String expected = "Czytam.pl";
        //when
        String actual = books.get(0).bookstore();
        //then
        AssertJUnit.assertEquals(expected,actual);
    }
}
