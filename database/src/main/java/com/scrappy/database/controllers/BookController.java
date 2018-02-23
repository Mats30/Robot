package com.scrappy.database.controllers;

import com.scrappy.database.dto.BookDTO;
import com.scrappy.database.services.BookService;
import com.scrappy.database.services.BookServiceQualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/byTitle")
    public List<BookDTO> getBooksByTitle(@RequestParam String title) {
        return service.findByTitle(title);
    }
}
