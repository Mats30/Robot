package com.scrappy.database.controllers;

import com.scrappy.database.dto.BookDTO;
import com.scrappy.database.model.Book;
import com.scrappy.database.services.BookService;
import com.scrappy.database.services.BookServiceQualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/test")
    public List<BookDTO> getAllBooks() {
        return service.test();
    }


  @GetMapping("/findAll")
  public List<BookDTO> getAllBooks1() {

    return service.findAll();
  }

  @GetMapping("/all")
    public Page<Book> getBooks(Pageable pageable) {
//      service.save(new Book("abc"));
//      service.save(new Book("crf"));
//      service.save(new Book("bga"));
//      service.save(new Book("etr"));
//      service.save(new Book("dyu"));

      return service.findAll(pageable);
    }

    @GetMapping("/byTitle")
    public List<BookDTO> getBooksByTitle(@RequestParam String title) {
        return service.findByTitle(title);
    }

    @PostMapping(path = "/save", consumes = "application/json", produces = "application/json")
    public void save(@RequestBody Book book) {
        book.setBookDetails(book.getBookDetails());
//        book.getBookDetails().setBook(book);
        service.save(book);
    }
}
