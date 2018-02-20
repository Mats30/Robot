package com.scrappy.database.controllers;

import com.scrappy.database.model.Book;
import com.scrappy.database.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class BookController {
    private BooksRepository repository;

    @Autowired
    public BookController(BooksRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getBooks() {
        return repository.findAll();
    }
}
