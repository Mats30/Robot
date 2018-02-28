package com.scrappy.scrapper.api.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volumes;
import com.scrappy.scrapper.common.ScrappedBook;
import com.scrappy.scrapper.html.model.BookStore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class GoogleBooksProvider implements GoogleBookAPI {
    private static final String API_KEY = "AIzaSyC4jZhkBh1TDZ_WF7y5_h10VkikGzb8_D4";
    private static final Logger logger = Logger.getLogger(GoogleBooksProvider.class.getName());
    private static final BookStore BOOKSTORE = BookStore.GOOGLE;
    private final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    @Override
    public List<ScrappedBook> query() throws Exception {
        final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
            .setApplicationName("Scrappy")
            .setGoogleClientRequestInitializer(new BooksRequestInitializer(API_KEY))
            .build();
        final List<ScrappedBook> scrappedBooks = new ArrayList<>();
        final Books.Volumes.List volumesList = books.volumes().list("sql");
        final Volumes volumes = volumesList.execute();
        volumes.getItems().forEach(book -> {
            try {
                scrappedBooks.add(ScrappedBook.builder()
                    .setTitle(book.getVolumeInfo().getTitle())
                    .setAuthor(book.getVolumeInfo().getAuthors().stream().collect(Collectors.joining(",")))
                    .setListPrice(BigDecimal.valueOf(book.getSaleInfo().getListPrice().getAmount()))
                    .setDiscountPrice(BigDecimal.valueOf(book.getSaleInfo().getRetailPrice().getAmount()))
                    .setBookstore(BOOKSTORE)
                    .setUrl(book.getVolumeInfo().getInfoLink())
                    .setIsbn(book.getVolumeInfo().getIndustryIdentifiers().get(0).getIdentifier())
                    .setGenre(book.getVolumeInfo().getCategories().stream().collect(Collectors.joining(",")))
                    .build());
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

        });
        return scrappedBooks;
    }
}
