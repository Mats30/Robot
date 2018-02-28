package com.scrappy.database.services;

import com.scrappy.database.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * It defines the methods that need to appear in the service layer implementation.
 *
 * @version 1.0-RC
 * @since 2018-02-20
 */

public interface BookService {

    Page<Book> findAll(Pageable pageable);

    void save(Book book);
    
}
