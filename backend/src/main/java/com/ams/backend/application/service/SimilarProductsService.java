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

    public List<ProductDTO> getSimilarProducts(Long productId) {
        List<Long> similarIds = productClient.getSimilarProductIds(productId);

        return similarIds.stream().map(productClient::getProductById).filter(Objects::nonNull).toList();
    }
}
