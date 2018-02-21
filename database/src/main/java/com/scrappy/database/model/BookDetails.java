package com.scrappy.database.model;


import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
public class BookDetails {

  private BigDecimal basePrice;

  private BigDecimal promoPrice;

  @Enumerated
  private Genre genre;

  @OneToOne
  private Book book;

}
