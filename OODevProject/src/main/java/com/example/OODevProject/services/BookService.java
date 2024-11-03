package com.example.OODevProject.services;

import com.example.OODevProject.models.Book;
import com.example.OODevProject.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    public List<Book> getAllBooks() {
        logger.info("Fetching all books from the database");
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        logger.info("Fetching book by ID: {}", id);
        return bookRepository.findById(id);
    }

    public Book addBook(Book book) {
        logger.info("Adding new book: {}", book);
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book bookDetails) {
        logger.info("Updating book with ID: {}", id);
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(bookDetails.getTitle());
                    book.setAuthor(bookDetails.getAuthor());
                    book.setIsbn(bookDetails.getIsbn());
                    book.setAvailable(bookDetails.isAvailable());
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public List<Book> searchBooks(String title, String author, String isbn) {
        logger.info("Searching books with title: {}, author: {}, isbn: {}", title, author, isbn);
        return bookRepository.findAll((Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }
            if (author != null && !author.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("author")), "%" + author.toLowerCase() + "%"));
            }
            if (isbn != null && !isbn.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("isbn")), "%" + isbn.toLowerCase() + "%"));
            }

            // If no criteria are provided, return an empty criteriaBuilder
            if (predicates.isEmpty()) {
                return criteriaBuilder.conjunction(); // equivalent to "SELECT *"
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }


    public void deleteBook(Long id) {
        logger.info("Deleting book with ID: {}", id);
        bookRepository.deleteById(id);
    }
}
