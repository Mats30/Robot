package com.scrappy.database.controllers;

import com.scrappy.database.model.Book;
import com.scrappy.database.services.BookService;
import com.scrappy.database.services.BookServiceQualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;


/**
 * Exposes REST endpoints for clients to obtain data from.
 *
 * @version 1.0-RC
 * @since 2018-02-20
 */

@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    private BookService service;

    @Autowired
    @BookServiceQualifier(type = BookServiceQualifier.BookServiceType.POSTGRESQL)
    public BookController(BookService service) {
        this.service = service;
    }


    @GetMapping("/all")
    public Page<Book> getBooks(Pageable pageable) {

        return service.findAll(pageable);
    }

    @PostMapping(path = "/save", consumes = "application/json", produces = "application/json")
    public void save(@RequestBody Book book) {
        book.setBookDetails(book.getBookDetails());
        service.save(book);
    }
}
