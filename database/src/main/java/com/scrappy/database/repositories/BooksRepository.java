package com.scrappy.database.repositories;

import com.scrappy.database.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "books")
public interface BooksRepository extends JpaRepository<Book, Long> {
}
