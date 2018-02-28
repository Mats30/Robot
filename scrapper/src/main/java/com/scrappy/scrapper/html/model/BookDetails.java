package com.scrappy.scrapper.html.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement
@JsonSerialize
public class BookDetails {
    private BigDecimal basePrice;

    @Override
    public String toString() {
        return "BookDetails{" +
                "basePrice=" + basePrice +
                ", promoPrice=" + promoPrice +
                ", genre='" + genre + '\'' +
                '}';
    }

    private BigDecimal promoPrice;

    @JsonIgnore
    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    private String genre;

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
