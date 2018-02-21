package com.scrappy.database.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BookDetailsDTO implements Serializable {
    private BigDecimal basePrice;
    private BigDecimal promoPrice;
    private String genre;

    public BookDetailsDTO(BigDecimal basePrice, BigDecimal promoPrice, String genre) {
        this.basePrice = basePrice;
        this.promoPrice = promoPrice;
        this.genre = genre;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public BigDecimal getPromoPrice() {
        return promoPrice;
    }

    public String getGenre() {
        return genre;
    }
}
