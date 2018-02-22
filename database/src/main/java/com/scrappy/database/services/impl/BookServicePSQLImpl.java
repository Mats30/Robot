package com.scrappy.database.services.impl;

import com.scrappy.database.dto.BookDTO;
import com.scrappy.database.dto.BookDetailsDTO;
import com.scrappy.database.model.BookStore;
import com.scrappy.database.repositories.BookRepository;
import com.scrappy.database.services.BookService;
import com.scrappy.database.services.BookServiceQualifier;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<BookDTO> findAll() {
        return null;
    }

    @Override
    public List<BookDTO> findByTitle(String title) {
        return null;
    }

    @Override
    public List<BookDTO> test() {
        return Arrays.asList(new BookDTO("Potop", "Henryk Siekiewicz", new BookDetailsDTO(new BigDecimal(7999), new BigDecimal(4999), "Historyczna"), BookStore.NIEDZIELA),
                new BookDTO("Pan Wolodyjowski", "Henryk Sienkiewicz", new BookDetailsDTO(new BigDecimal(8999), new BigDecimal(3999), "Historyczna"), BookStore.HELION),
                new BookDTO("Swiat dysku", "Terry Prachett", new BookDetailsDTO(new BigDecimal(19999), new BigDecimal(4999), "SCI-FI"), BookStore.HELION),
                new BookDTO("Effective Java 3rd Edition", "Joshua Bloch", new BookDetailsDTO(new BigDecimal(4999), new BigDecimal(2999), "SCI-FI"), BookStore.GANDALF));
    }

}
