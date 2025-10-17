package com.ams.backend.application.service;

import com.ams.backend.application.dto.ProductDTO;
import com.ams.backend.infrastructure.client.ProductClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
public class SimilarProductsService {

    private final ProductClient productClient;
    private final Executor executor;

    public SimilarProductsService(ProductClient productClient, Executor executor) {
        this.productClient = productClient;
        this.executor = executor;
    }

    public ProductDTO getProductById(Long productId) {
        return productClient.getProductById(productId);
    }

    public List<ProductDTO> getSimilarProducts(Long productId) {
        // Get similar product IDs
        List<Long> similarIds = productClient.getSimilarProductIds(productId);

        // Get product details for each ID
        // Fetch product details asynchronously
        List<CompletableFuture<ProductDTO>> futures = similarIds.stream()
                .map(id -> CompletableFuture.supplyAsync(() -> productClient.getProductById(id), executor))
                .toList();

        // Wait for all calls to complete and filter nulls
        return futures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .toList();
    }
}
