package com.example.OODevProject.repositories;

import com.example.OODevProject.models.BorrowedBook;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BorrowedBookRepository extends MongoRepository<BorrowedBook, String> {
    Optional<BorrowedBook> findByBookId(String bookId);
}
