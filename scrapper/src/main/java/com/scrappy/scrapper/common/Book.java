package com.scrappy.scrapper.common;

import com.google.auto.value.AutoValue;

import java.math.BigDecimal;

@AutoValue
abstract public class Book {
  abstract public String title();
  abstract String author();
  abstract BigDecimal discountPrice();
  abstract BigDecimal listPrice();
  abstract String bookstore();
  abstract String isbn();
  abstract String url();
  
  public static Builder builder() {
    return new AutoValue_Book.Builder();
  }
  
  @AutoValue.Builder
  abstract static public class Builder {
    abstract public Builder setTitle(String title);
    abstract public Builder setAuthor(String author);
    abstract public Builder setDiscountPrice(BigDecimal discountPrice);
    abstract public Builder setListPrice(BigDecimal listPrice);
    abstract public Builder setBookstore(String bookstore);
    abstract public Builder setIsbn(String isbn);
    abstract public Builder setUrl(String url);
    abstract public Book build();
  }
}
