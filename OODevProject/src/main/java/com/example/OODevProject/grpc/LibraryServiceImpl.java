package com.example.OODevProject.grpc;

import com.example.OODevProject.grpc.LibraryProto;
import com.example.OODevProject.grpc.LibraryServiceGrpc;
import com.example.OODevProject.models.BorrowedBook;
import com.example.OODevProject.repositories.BorrowedBookRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Optional;

@GrpcService
public class LibraryServiceImpl extends LibraryServiceGrpc.LibraryServiceImplBase {

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;

    private static final Logger logger = LoggerFactory.getLogger(LibraryServiceImpl.class);

    @Override
    public void borrowBook(LibraryProto.BorrowBookRequest request, StreamObserver<LibraryProto.BorrowBookResponse> responseObserver) {
        logger.info("Received request to borrow book with ID: {}", request.getBookId());

        try {
            Optional<BorrowedBook> borrowedBook = borrowedBookRepository.findByBookId(request.getBookId());

            LibraryProto.BorrowBookResponse response;
            if (borrowedBook.isPresent()) {
                response = LibraryProto.BorrowBookResponse.newBuilder()
                        .setSuccess(false)
                        .setMessage("Book is already borrowed")
                        .build();
                logger.info("Book with ID {} is already borrowed", request.getBookId());
            } else {
                BorrowedBook newBorrowedBook = new BorrowedBook();
                newBorrowedBook.setUserId(request.getUserId());
                newBorrowedBook.setBookId(request.getBookId());
                newBorrowedBook.setBorrowDate(LocalDate.now().toString());
                borrowedBookRepository.save(newBorrowedBook);

                response = LibraryProto.BorrowBookResponse.newBuilder()
                        .setSuccess(true)
                        .setMessage("Book borrowed successfully")
                        .build();
                logger.info("Book with ID {} successfully borrowed", request.getBookId());
            }

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            logger.error("Error borrowing book with ID: {}", request.getBookId(), e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void returnBook(LibraryProto.ReturnBookRequest request, StreamObserver<LibraryProto.ReturnBookResponse> responseObserver) {
        logger.info("Received request to return book with ID: {}", request.getBookId());

        try {
            Optional<BorrowedBook> borrowedBook = borrowedBookRepository.findByBookId(request.getBookId());

            LibraryProto.ReturnBookResponse response;
            if (borrowedBook.isPresent()) {
                borrowedBookRepository.delete(borrowedBook.get());
                response = LibraryProto.ReturnBookResponse.newBuilder()
                        .setSuccess(true)
                        .setMessage("Book returned successfully")
                        .build();
                logger.info("Book with ID {} successfully returned", request.getBookId());
            } else {
                response = LibraryProto.ReturnBookResponse.newBuilder()
                        .setSuccess(false)
                        .setMessage("Book not found in borrowed records")
                        .build();
                logger.warn("Book with ID {} not found in borrowed records", request.getBookId());
            }

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            logger.error("Error returning book with ID: {}", request.getBookId(), e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void checkBookAvailability(LibraryProto.CheckAvailabilityRequest request, StreamObserver<LibraryProto.CheckAvailabilityResponse> responseObserver) {
        logger.info("Received request to check availability of book with ID: {}", request.getBookId());

        try {
            boolean isAvailable = !borrowedBookRepository.findByBookId(request.getBookId()).isPresent();

            LibraryProto.CheckAvailabilityResponse response = LibraryProto.CheckAvailabilityResponse.newBuilder()
                    .setAvailable(isAvailable)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

            logger.info("Checked availability of book with ID {}: {}", request.getBookId(), isAvailable ? "Available" : "Not Available");

        } catch (Exception e) {
            logger.error("Error checking availability for book with ID: {}", request.getBookId(), e);
            responseObserver.onError(e);
        }
    }
}
