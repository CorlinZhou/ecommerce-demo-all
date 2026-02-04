package com.zxr.backend.service;

import com.zxr.backend.model.Product;
import com.zxr.backend.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Product service class
 * Responsible for handling product-related business logic
 */
@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    
    private final ProductRepository repository;

    /**
     * Construct product service
     * @param repository Product repository
     */
    public ProductService(ProductRepository repository) {
        this.repository = repository;
        logger.info("Product service initialized");
    }

    /**
     * Get all products
     * @return Product collection
     */
    public Collection<Product> getProducts() {
        logger.info("Getting all products");
        Collection<Product> products = repository.findAll();
        logger.info("Retrieved {} products", products.size());
        return products;
    }

    /**
     * Get product by ID
     * @param id Product ID
     * @return Product object
     * @throws IllegalArgumentException if ID is null or invalid
     */
    public Product getProduct(Long id) {
        if (id == null || id <= 0) {
            logger.error("Invalid product ID: {}", id);
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
        logger.info("Getting product by ID: {}", id);
        Product product = repository.findById(id);
        if (product == null) {
            logger.warn("Product not found with ID: {}", id);
        }
        return product;
    }
}