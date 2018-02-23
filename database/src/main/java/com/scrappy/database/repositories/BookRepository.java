package com.scrappy.database.repositories;

import com.scrappy.database.dto.BookDTO;
import com.scrappy.database.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  List<Book> findBooksByTitle(String title);


  Page<Book> findAll(Pageable pageable);
 }
