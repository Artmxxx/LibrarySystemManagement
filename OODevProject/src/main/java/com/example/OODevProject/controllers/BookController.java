package com.example.OODevProject.controllers;

import com.example.OODevProject.grpc.LibraryProto;
import com.example.OODevProject.grpc.LibraryServiceGrpc;
import com.example.OODevProject.models.Book;
import com.example.OODevProject.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final LibraryServiceGrpc.LibraryServiceBlockingStub grpcClient;
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    public BookController(BookService bookService, LibraryServiceGrpc.LibraryServiceBlockingStub grpcClient) {
        this.bookService = bookService;
        this.grpcClient = grpcClient;
    }

    // REST endpoint to get all books
    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        try {
            List<Book> books = bookService.getAllBooks();
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            logger.error("Error fetching all books", e);
            return ResponseEntity.status(500).body("Internal Server Error while fetching books");
        }
    }

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        try {
            Book savedBook = bookService.addBook(book);
            return ResponseEntity.ok(savedBook);
        } catch (Exception e) {
            logger.error("Error adding book", e);
            return ResponseEntity.status(500).body("Internal Server Error while adding book");
        }
    }

    // Other endpoints follow similar patterns with exception handling

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        try {
            Book updatedBook = bookService.updateBook(id, bookDetails);
            return ResponseEntity.ok(updatedBook);
        } catch (RuntimeException e) {
            logger.error("Error updating book with ID: " + id, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating book with ID: " + id, e);
            return ResponseEntity.status(500).body("Internal Server Error while updating book");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting book with ID: " + id, e);
            return ResponseEntity.status(500).body("Internal Server Error while deleting book");
        }
    }

    // REST endpoint to search for books by criteria
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam(required = false) String title,
                                                  @RequestParam(required = false) String author,
                                                  @RequestParam(required = false) String isbn) {
        try {
            List<Book> books = bookService.searchBooks(title, author, isbn);
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            logger.error("Error searching books", e);
            return ResponseEntity.status(500).body(null);
        }
    }

}
