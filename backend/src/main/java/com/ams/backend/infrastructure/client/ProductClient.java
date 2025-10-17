package com.ams.backend.infrastructure.client;

import com.ams.backend.application.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class ProductClient {
    private final RestTemplate restTemplate;
    private final String externalBaseUrl;

    public ProductClient(RestTemplate restTemplate,
                         @Value("${external.api.url:http://localhost:3001}") String externalBaseUrl) {
        this.restTemplate = restTemplate;
        this.externalBaseUrl = externalBaseUrl;
    }

    public List<Long> getSimilarProductIds(Long productId) {
        String url = String.format("%s/product/%s/similarids", externalBaseUrl, productId);
        Long[] similarIds = restTemplate.getForObject(url, Long[].class);
        if (similarIds == null) {
            return Collections.emptyList(); // Return empty list if no similar IDs are found
        }
        return List.of(similarIds);
    }

    public ProductDTO getProductById(Long productId) {
        try {
            String url = String.format("%s/product/%s", externalBaseUrl, productId);
            return restTemplate.getForObject(url, ProductDTO.class);
        } catch (RestClientException e) {
            return null; // Return null if the product is not found
        }
    }
}
