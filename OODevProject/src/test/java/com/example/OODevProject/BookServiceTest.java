package com.example.OODevProject;

import com.example.OODevProject.models.Book;
import com.example.OODevProject.repositories.BookRepository;
import com.example.OODevProject.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBooks_ShouldReturnAllBooks() {
        List<Book> mockBooks = List.of(
                new Book("To Kill a Mockingbird", "Harper Lee", "9780061120084", true),
                new Book("1984", "George Orwell", "9780451524935", true)
        );

        when(bookRepository.findAll()).thenReturn(mockBooks);

        List<Book> books = bookService.getAllBooks();

        assertNotNull(books);
        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById_ShouldReturnBook_WhenBookExists() {
        Book mockBook = new Book("1984", "George Orwell", "9780451524935", true);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(mockBook));

        Optional<Book> book = bookService.getBookById(1L);

        assertTrue(book.isPresent());
        assertEquals("1984", book.get().getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookById_ShouldReturnEmpty_WhenBookDoesNotExist() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Book> book = bookService.getBookById(99L);

        assertFalse(book.isPresent());
        verify(bookRepository, times(1)).findById(99L);
    }

    @Test
    void addBook_ShouldSaveAndReturnBook() {
        Book newBook = new Book("The Catcher in the Rye", "J.D. Salinger", "9780316769488", true);
        when(bookRepository.save(newBook)).thenReturn(newBook);

        Book savedBook = bookService.addBook(newBook);

        assertNotNull(savedBook);
        assertEquals("The Catcher in the Rye", savedBook.getTitle());
        verify(bookRepository, times(1)).save(newBook);
    }

    @Test
    void updateBook_ShouldUpdateAndReturnBook_WhenBookExists() {
        Book existingBook = new Book("The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", true);
        existingBook.setId(1L);
        Book updatedDetails = new Book("The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", false);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        Book updatedBook = bookService.updateBook(1L, updatedDetails);

        assertNotNull(updatedBook);
        assertEquals("The Great Gatsby", updatedBook.getTitle());
        assertFalse(updatedBook.isAvailable());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    void updateBook_ShouldThrowException_WhenBookDoesNotExist() {
        Book updatedDetails = new Book("Nonexistent Book", "Unknown Author", "0000000000", false);

        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> bookService.updateBook(99L, updatedDetails));
        assertEquals("Book not found", exception.getMessage());
        verify(bookRepository, times(1)).findById(99L);
        verify(bookRepository, times(0)).save(any(Book.class));
    }

    @Test
    void deleteBook_ShouldDeleteBook_WhenBookExists() {
        Long bookId = 1L;
        doNothing().when(bookRepository).deleteById(bookId);

        bookService.deleteBook(bookId);

        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    void deleteBook_ShouldDoNothing_WhenBookDoesNotExist() {
        Long bookId = 99L;
        doNothing().when(bookRepository).deleteById(bookId);

        bookService.deleteBook(bookId);

        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    void searchBooks_ShouldReturnBooksMatchingCriteria() {
        List<Book> mockBooks = List.of(new Book("1984", "George Orwell", "9780451524935", true));
        when(bookRepository.findAll(any(Specification.class))).thenReturn(mockBooks);

        List<Book> books = bookService.searchBooks("1984", null, null);

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("1984", books.get(0).getTitle());
        verify(bookRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    void searchBooks_ShouldReturnAllBooks_WhenNoCriteriaProvided() {
        List<Book> mockBooks = List.of(
                new Book("To Kill a Mockingbird", "Harper Lee", "9780061120084", true),
                new Book("1984", "George Orwell", "9780451524935", true)
        );
        when(bookRepository.findAll(any(Specification.class))).thenReturn(mockBooks);

        List<Book> books = bookService.searchBooks(null, null, null);

        assertNotNull(books);
        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    void searchBooks_ShouldHandleNullTitleAndReturnBooks_WhenOtherCriteriaMatch() {
        List<Book> mockBooks = List.of(new Book("To Kill a Mockingbird", "Harper Lee", "9780061120084", true));
        when(bookRepository.findAll(any(Specification.class))).thenReturn(mockBooks);

        List<Book> books = bookService.searchBooks(null, "Harper Lee", null);

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("To Kill a Mockingbird", books.get(0).getTitle());
        verify(bookRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    void searchBooks_ShouldThrowException_WhenRepositoryThrowsError() {
        when(bookRepository.findAll(any(Specification.class))).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> bookService.searchBooks("Title", "Author", "ISBN"));
        assertEquals("Database error", exception.getMessage());
        verify(bookRepository, times(1)).findAll(any(Specification.class));
    }
}
