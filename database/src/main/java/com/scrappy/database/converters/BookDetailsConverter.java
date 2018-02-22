package com.scrappy.database.converters;

import com.scrappy.database.dto.BookDetailsDTO;
import com.scrappy.database.model.BookDetails;

import java.util.List;
import java.util.stream.Collectors;

public class BookDetailsConverter {

    public static BookDetailsDTO convert(BookDetails bookDetails){
        return new BookDetailsDTO(bookDetails.getBasePrice(), bookDetails.getPromoPrice(), bookDetails.getGenre());
    }

    public static List<BookDetailsDTO> convert(List<BookDetails> bookDetails) {
        return bookDetails.stream().map(BookDetailsConverter::convert).collect(Collectors.toList());
    }
}
