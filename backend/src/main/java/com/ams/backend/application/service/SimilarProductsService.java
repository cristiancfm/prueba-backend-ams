package com.ams.backend.application.service;

import com.ams.backend.application.dto.ProductDTO;
import com.ams.backend.infrastructure.client.ProductClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Service
public class SimilarProductsService {
    private final ProductClient productClient;

    public SimilarProductsService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public Mono<ProductDTO> getProductById(Long productId) {
        return productClient.getProductById(productId);
    }

    public Mono<List<ProductDTO>> getSimilarProducts(Long productId) {
        return productClient.getSimilarProductIds(productId)
                .flatMapMany(Flux::fromIterable)
                .flatMap(productClient::getProductById) // Get product details for each ID
                .filter(Objects::nonNull) // Filter nulls
                .collectList();
    }
}
