package com.example.OODevProject.repositories;

import com.example.OODevProject.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    // Additional query methods can be defined here if needed
}
