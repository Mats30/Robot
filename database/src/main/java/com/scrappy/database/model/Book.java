package com.scrappy.database.model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String title;

  @OneToOne(mappedBy = "book")
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
