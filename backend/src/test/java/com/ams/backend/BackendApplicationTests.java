package com.ams.backend;

import com.ams.backend.application.service.SimilarProductsService;
import com.ams.backend.infrastructure.client.ProductClient;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@SpringBootTest
class BackendApplicationTests {

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private SimilarProductsService service;

    @Test
    void contextLoads() {
    }

    @Test
    void testGetSimilarProducts_ClientFails_ReturnsEmptyList() {
        // Simulate error when fetching similar product IDs
        Mockito.when(productClient.getSimilarProductIds(1L))
                .thenReturn(Mono.error(new RuntimeException("Service unavailable")));

        StepVerifier.create(service.getSimilarProducts(1L))
                .expectNextMatches(List::isEmpty) // Expect empty list
                .verifyComplete();
    }

    @Test
    void testGetProductById_ClientFails_ReturnsEmptyMono() {
        // Simulate error when fetching product by ID
        Mockito.when(productClient.getProductById(1L))
                .thenReturn(Mono.error(new RuntimeException("Service unavailable")));

        StepVerifier.create(service.getProductById(1L))
                .expectNextCount(0) // Expect no items
                .verifyComplete();
    }
}
