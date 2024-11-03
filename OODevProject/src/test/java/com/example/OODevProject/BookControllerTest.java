package com.example.OODevProject;

import com.example.OODevProject.controllers.BookController;
import com.example.OODevProject.grpc.LibraryProto;
import com.example.OODevProject.grpc.LibraryServiceGrpc;
import com.example.OODevProject.models.Book;
import com.example.OODevProject.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private LibraryServiceGrpc.LibraryServiceBlockingStub grpcClient;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void getAllBooks_ShouldReturnListOfBooks() throws Exception {
        List<Book> books = List.of(new Book("1984", "George Orwell", "9780451524935", true));
        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("1984"));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void addBook_ShouldAddAndReturnBook() throws Exception {
        Book newBook = new Book("The Catcher in the Rye", "J.D. Salinger", "9780316769488", true);
        when(bookService.addBook(any(Book.class))).thenReturn(newBook);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("The Catcher in the Rye"));

        verify(bookService, times(1)).addBook(any(Book.class));
    }

    @Test
    void borrowBook_ShouldInvokeGrpcClient() {
        // Arrange
        LibraryProto.BorrowBookRequest request = LibraryProto.BorrowBookRequest.newBuilder()
                .setUserId("user123")
                .setBookId("book123")
                .build();

        LibraryProto.BorrowBookResponse response = LibraryProto.BorrowBookResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Book borrowed successfully")
                .build();

        when(grpcClient.borrowBook(request)).thenReturn(response);

        // Act
        LibraryProto.BorrowBookResponse actualResponse = grpcClient.borrowBook(request);

        // Assert
        assertEquals(response.getMessage(), actualResponse.getMessage());
        assertTrue(actualResponse.getSuccess());

        verify(grpcClient, times(1)).borrowBook(request);
    }
}