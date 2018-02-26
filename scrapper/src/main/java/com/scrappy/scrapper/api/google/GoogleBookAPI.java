package com.scrappy.scrapper.api.google;

import com.scrappy.scrapper.common.ScrappedBook;

import java.util.List;

public interface GoogleBookAPI {
    List<ScrappedBook> query() throws Exception;
}
