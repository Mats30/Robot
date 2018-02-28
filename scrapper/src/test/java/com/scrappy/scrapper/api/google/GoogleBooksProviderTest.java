package com.scrappy.scrapper.api.google;

import com.scrappy.scrapper.common.ScrappedBook;
import org.testng.annotations.Test;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Test
public class GoogleBooksProviderTest {

    public void queryBook() throws Exception {
        //given
        GoogleBookAPI googleBookAPI = new GoogleBooksProvider();

        //when
        List<ScrappedBook> query = googleBookAPI.query();

        //then
        assertThat(query).isNotEmpty();
    }
}