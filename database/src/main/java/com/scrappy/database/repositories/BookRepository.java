package com.scrappy.database.repositories;

import com.scrappy.database.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Serves as the repository to persisting objects in the database.
 *
 * @version 1.0-SNAPSHOT
 * @since 2018-02-20
 */

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findAll(Pageable pageable);
}
