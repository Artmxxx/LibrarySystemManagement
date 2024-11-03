package com.example.OODevProject;

import com.example.OODevProject.grpc.LibraryProto;
import com.example.OODevProject.grpc.LibraryServiceImpl;
import com.example.OODevProject.models.BorrowedBook;
import com.example.OODevProject.repositories.BorrowedBookRepository;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceGrpcTest {

    @Mock
    private BorrowedBookRepository borrowedBookRepository;

    @InjectMocks
    private LibraryServiceImpl libraryServiceImpl; // Use the concrete class

    @Test
    void testBorrowBookWhenBookIsAvailable() {
        LibraryProto.BorrowBookRequest request = LibraryProto.BorrowBookRequest.newBuilder()
                .setUserId("user123")
                .setBookId("book123")
                .build();

        StreamObserver<LibraryProto.BorrowBookResponse> responseObserver = mock(StreamObserver.class);

        when(borrowedBookRepository.findByBookId("book123")).thenReturn(Optional.empty());

        libraryServiceImpl.borrowBook(request, responseObserver);

        verify(borrowedBookRepository, times(1)).save(any(BorrowedBook.class));
        verify(responseObserver).onNext(argThat(response -> response.getSuccess() && response.getMessage().equals("Book borrowed successfully")));
        verify(responseObserver).onCompleted();
    }

    @Test
    void testBorrowBookWhenBookIsAlreadyBorrowed() {
        LibraryProto.BorrowBookRequest request = LibraryProto.BorrowBookRequest.newBuilder()
                .setUserId("user123")
                .setBookId("book123")
                .build();

        StreamObserver<LibraryProto.BorrowBookResponse> responseObserver = mock(StreamObserver.class);
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setBookId("book123");

        when(borrowedBookRepository.findByBookId("book123")).thenReturn(Optional.of(borrowedBook));

        libraryServiceImpl.borrowBook(request, responseObserver);

        verify(borrowedBookRepository, never()).save(any(BorrowedBook.class));
        verify(responseObserver).onNext(argThat(response -> !response.getSuccess() && response.getMessage().equals("Book is already borrowed")));
        verify(responseObserver).onCompleted();
    }
}
