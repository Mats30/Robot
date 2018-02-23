package com.scrappy.database.model;

import javax.persistence.*;

@Entity

public class Book {
    /**
     * DO NOT IMPLEMENT .toString() METHOD IN THIS CLASS! IT WILL CAUSE CYCLIC DEPENDENCY AND STACK OVERFLOW ERROR!
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    private BookDetails bookDetails;

    private String author;

    @Enumerated
    private BookStore bookStore;

    public Book() {
    }

    public Book(String title) {
        this.title = title;
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

    public BookStore getBookStore() {
        return bookStore;
    }
}
