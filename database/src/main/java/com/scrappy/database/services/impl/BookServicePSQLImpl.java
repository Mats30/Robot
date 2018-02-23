package com.scrappy.database.services.impl;

import com.scrappy.database.model.Book;
import com.scrappy.database.repositories.BookRepository;
import com.scrappy.database.services.BookService;
import com.scrappy.database.services.BookServiceQualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


/**
 * It provides the service layer that serves as an intermediary between the controller and the repository.
 *
 * @version 1.0-SNAPSHOT
 * @since 2018-02-20
 */

@Component
@BookServiceQualifier(type = BookServiceQualifier.BookServiceType.POSTGRESQL)
public class BookServicePSQLImpl implements BookService {

    private BookRepository repository;

    @Autowired
    public BookServicePSQLImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void save(Book book) {
        System.out.println(book);
        repository.save(book);
    }

}
