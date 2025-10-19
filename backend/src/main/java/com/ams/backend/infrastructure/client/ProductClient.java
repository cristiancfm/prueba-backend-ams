package com.ams.backend.infrastructure.client;

import com.ams.backend.application.dto.ProductDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Component
public class ProductClient {
    private final WebClient webClient;

    public ProductClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<Long>> getSimilarProductIds(Long productId) {
        return webClient.get()
                .uri("/product/{id}/similarids", productId)
                .retrieve()
                .bodyToFlux(Long.class)
                .timeout(Duration.ofSeconds(3))
                .collectList()
                .onErrorReturn(Collections.emptyList());
    }

    public Mono<ProductDTO> getProductById(Long productId) {
        return webClient.get()
                .uri("/product/{id}", productId)
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .timeout(Duration.ofSeconds(3))
                .onErrorResume(e -> Mono.empty());
    }
}
