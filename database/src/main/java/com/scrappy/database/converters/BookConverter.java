package com.scrappy.database.converters;

import com.scrappy.database.dto.BookDTO;
import com.scrappy.database.model.Book;
import java.util.List;
import java.util.stream.Collectors;

public class BookConverter {
    static BookDTO convert(Book book){
        return new BookDTO(book.getTitle(), book.getAuthor(), BookDetailsConverter.convert(book.getBookDetails()), book.getBookStore());
    }

    public static List<BookDTO> convert(List<Book> bookDetails) {

        return bookDetails.stream().map(BookConverter::convert).collect(Collectors.toList());
    }
}
