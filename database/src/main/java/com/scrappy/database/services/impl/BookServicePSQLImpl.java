package com.scrappy.database.services.impl;

import com.scrappy.database.dto.BookDTO;
import com.scrappy.database.dto.BookDetailsDTO;
import com.scrappy.database.model.Book;
import com.scrappy.database.model.BookStore;
import com.scrappy.database.repositories.BookRepository;
import com.scrappy.database.services.BookService;
import com.scrappy.database.services.BookServiceQualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

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
  public List<BookDTO> findByTitle(String title) {
    return null;
  }

  @Override
  public List<BookDTO> test() {
    return Arrays.asList(new BookDTO("Potop", "Henryk Siekiewicz", new BookDetailsDTO(new BigDecimal(7999), new BigDecimal(4999), "Historyczna"), BookStore.NIEDZIELA));
  }

  @Override
  public void save(Book book) {
    System.out.println(book);
    repository.save(book);
  }

}
