package com.ams.backend.infrastructure.rest;

import com.ams.backend.application.dto.ProductDTO;
import com.ams.backend.application.service.SimilarProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final SimilarProductsService similarProductsService;

    public ProductController(SimilarProductsService similarProductsService) {
        this.similarProductsService = similarProductsService;
    }

    @GetMapping("/{productId}/similar")
    public ResponseEntity<List<ProductDTO>> getSimilarProducts(@PathVariable Long productId) {
        // Check if the original product exists
        ProductDTO originalProduct = similarProductsService.getProductById(productId);
        if (originalProduct == null) {
            return ResponseEntity.notFound().build();
        }

        List<ProductDTO> similarProducts = similarProductsService.getSimilarProducts(productId);
        return ResponseEntity.ok(similarProducts);
    }
}
