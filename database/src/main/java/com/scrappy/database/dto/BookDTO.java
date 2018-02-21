package com.scrappy.database.dto;

import com.scrappy.database.model.BookStore;
import java.io.Serializable;

public class BookDTO implements Serializable {
    private String title;
    private String author;
    private BookDetailsDTO bookDetails;
    private BookStore bookStore;

    public BookDTO(String title, String author, BookDetailsDTO bookDetails, BookStore bookStore) {
        this.title = title;
        this.author = author;
        this.bookDetails = bookDetails;
        this.bookStore = bookStore;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public BookDetailsDTO getBookDetails() {
        return bookDetails;
    }

    public BookStore getBookStore() {
        return bookStore;
    }
}
