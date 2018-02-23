package com.scrappy.scrapper.postsender;

import com.scrappy.scrapper.common.ScrappedBook;
import com.scrappy.scrapper.html.model.Book;
import com.scrappy.scrapper.html.model.BookDetails;

/**
 * It converts the data on scrapped books to entities tha can be saved in the database.
 *
 * @version 1.0-SNAPSHOT
 * @since 2018-02-20
 */

public class EntityConverter {
    public Book convertScrappedToEntity(ScrappedBook scrappedBook) {
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
