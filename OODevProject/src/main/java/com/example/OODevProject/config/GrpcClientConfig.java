package com.example.OODevProject.config;

import com.example.OODevProject.grpc.LibraryServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    // Configure the gRPC channel
    @Bean
    public ManagedChannel managedChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext() // Use plaintext for local development
                .build();
    }

    // Configure the gRPC stub for LibraryService
    @Bean
    public LibraryServiceGrpc.LibraryServiceBlockingStub libraryServiceBlockingStub(ManagedChannel channel) {
        return LibraryServiceGrpc.newBlockingStub(channel);
    }
}
