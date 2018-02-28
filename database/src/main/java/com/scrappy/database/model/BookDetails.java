package com.scrappy.database.model;


import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Represents the BookDetails entity so as to persist it in the database.
 *
 * @version 1.0-RC
 * @since 2018-02-20
 */

@Entity
public class BookDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private BigDecimal basePrice;

    private BigDecimal promoPrice;

    private String genre;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(BigDecimal promoPrice) {
        this.promoPrice = promoPrice;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
