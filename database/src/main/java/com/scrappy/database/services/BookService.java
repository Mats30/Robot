package com.scrappy.database.services;

import com.scrappy.database.dto.BookDTO;

import java.util.List;


public interface BookService {
    List<BookDTO> findAll();

    List<BookDTO> findByTitle(String title);

    List<BookDTO> test();
    
}
