package com.ams.backend.application.service;

import com.ams.backend.application.dto.ProductDTO;
import com.ams.backend.infrastructure.client.ProductClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SimilarProductsService {

    private final ProductClient productClient;

    public SimilarProductsService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public ProductDTO getProductById(Long productId) {
        return productClient.getProductById(productId);
    }

    public List<ProductDTO> getSimilarProducts(Long productId) {
        // Get similar product IDs
        List<Long> similarIds = productClient.getSimilarProductIds(productId);

        // Get product details for each ID
        return similarIds.stream()
                .map(productClient::getProductById)
                .filter(Objects::nonNull)
                .toList();
    }
}
