package com.scrappy.database.controllers;

import com.scrappy.database.dto.BookDTO;
import com.scrappy.database.model.Book;
import com.scrappy.database.model.BookDetails;
import com.scrappy.database.services.BookService;
import com.scrappy.database.services.BookServiceQualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private BookService service;

    @Autowired
    @BookServiceQualifier(type = BookServiceQualifier.BookServiceType.POSTGRESQL)
    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/test")
    public List<BookDTO> getAllBooks() {
        return service.test();
    }

    @GetMapping("/all")
    public List<BookDTO> getBooks() {
        return service.findAll();
    }

    @GetMapping("/byTitle")
    public List<BookDTO> getBooksByTitle(@RequestParam String title) {
        return service.findByTitle(title);
    }

    @PostMapping(path = "/save", consumes = "application/json", produces = "application/json")
    public void save(@RequestBody Book book) {
        book.setBookDetails(book.getBookDetails());
        book.getBookDetails().setBook(book);
        service.save(book);
    }
}
