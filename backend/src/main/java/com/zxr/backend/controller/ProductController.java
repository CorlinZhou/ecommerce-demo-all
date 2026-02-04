package com.zxr.backend.controller;

import com.zxr.backend.model.Product;
import com.zxr.backend.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Product controller class
 * Handles product-related HTTP requests
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    
    private final ProductService service;

    /**
     * Construct product controller
     * @param service Product service
     */
    public ProductController(ProductService service) {
        this.service = service;
        logger.info("Product controller initialized");
    }

    /**
     * Get all products
     * @return Product collection
     */
    @GetMapping
    public ResponseEntity<Collection<Product>> getProducts() {
        logger.info("Received request to get all products");
        
        try {
            Collection<Product> products = service.getProducts();
            logger.info("Successfully retrieved {} products", products.size());
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Failed to retrieve products: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get product by ID
     * @param id Product ID
     * @return Product object
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        logger.info("Received request to get product, product ID: {}", id);
        
        try {
            Product product = service.getProduct(id);
            if (product != null) {
                logger.info("Successfully retrieved product: {}", product.getName());
                return ResponseEntity.ok(product);
            } else {
                logger.warn("Product not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            logger.error("Parameter error: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Failed to retrieve product: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}