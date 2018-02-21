package com.scrappy.scrapper.common;

import com.google.auto.value.AutoValue;

import java.math.BigDecimal;

@AutoValue
abstract public class Book {
  public abstract String title();
  public abstract String author();
  public abstract BigDecimal discountPrice();
  public abstract BigDecimal listPrice();
  public abstract String bookstore();
  public abstract String isbn();
  public abstract String url();

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
