package com.scrappy.scrapper.html.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@JsonSerialize
public class Book {

    private String title;

    private BookDetails bookDetails;

    private String author;

    private BookStore bookStore;

    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ScrappedBook{" +
                "title='" + title + '\'' +
                ", bookDetails=" + bookDetails.toString() +
                ", author='" + author + '\'' +
                ", bookStore=" + bookStore +
                '}';
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BookDetails getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(BookDetails bookDetails) {
        this.bookDetails = bookDetails;
    }

    public void setBookStore(BookStore bookStore) {
        this.bookStore = bookStore;
    }

    public BookStore getBookStore() {
        return bookStore;
    }
}
