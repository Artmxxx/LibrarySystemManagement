package com.example.OODevProject.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "borrowed_books")
public class BorrowedBook {

    @Id
    private String id;  // Use String for MongoDB ObjectId
    private String userId;
    private String bookId;
    private String borrowDate;

    // No-args constructor (required by frameworks like JPA and MongoDB)
    public BorrowedBook() {
    }

    // Parameterized constructor
    public BorrowedBook(String userId, String bookId, String borrowDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getBorrowDate() { return borrowDate; }
    public void setBorrowDate(String borrowDate) { this.borrowDate = borrowDate; }
}
