package com.scrappy.scrapper.common;

import com.google.auto.value.AutoValue;
import com.scrappy.scrapper.html.model.BookStore;

import java.math.BigDecimal;

@AutoValue
abstract public class ScrappedBook {
  public abstract String title();
  
  public abstract String author();
  
  public abstract BigDecimal discountPrice();
  
  public abstract BigDecimal listPrice();
  
  public abstract BookStore bookstore();
  
  public abstract String isbn();
  
  public abstract String url();

  public abstract String genre();

  public static Builder builder() {
    return new AutoValue_ScrappedBook.Builder();
  }
  
  @AutoValue.Builder
  abstract static public class Builder {
    abstract public Builder setTitle(String title);
    
    abstract public Builder setAuthor(String author);
    
    abstract public Builder setDiscountPrice(BigDecimal discountPrice);
    
    abstract public Builder setListPrice(BigDecimal listPrice);
    
    abstract public Builder setBookstore(BookStore bookstore);
    
    abstract public Builder setIsbn(String isbn);
    
    abstract public Builder setUrl(String url);

    abstract public Builder setGenre(String url);

    abstract public ScrappedBook build();
  }
  
}
