package com.scrappy.scrapper.postsender;

import com.scrappy.scrapper.common.ScrappedBook;
import com.scrappy.scrapper.html.model.Book;
import com.scrappy.scrapper.html.model.BookDetails;

public class UglyAsFuckConverterToRefactorLater {
    public Book convertAsUglyAsYouCan(ScrappedBook scrappedBook) {
        Book temp = new Book();
        new com.scrappy.scrapper.html.model.Book();
        BookDetails details = new BookDetails();
        details.setBasePrice(scrappedBook.listPrice());
        details.setPromoPrice(scrappedBook.discountPrice());
        details.setGenre("example");
        temp.setTitle(scrappedBook.title());
        temp.setAuthor(scrappedBook.author());
        temp.setBookDetails(details);
        temp.setAuthor(scrappedBook.author());
        temp.setBookStore(scrappedBook.bookstore());
        return temp;
    }
}
