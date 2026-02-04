package com.zxr.backend.repository;

import com.zxr.backend.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Random;

/**
 * Product repository class
 * Responsible for product data storage and access
 */
@Repository
public class ProductRepository {

    private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    /** Product storage container */
    private final Map<Long, Product> store = new ConcurrentHashMap<>();

    /**
     * Initialize product repository with sample data
     */
    public ProductRepository() {
        logger.info("Initializing product repository");
        store.put(1L, new Product(1L, "Red Fuji Apple", 1.99, getRandomStock()));
        store.put(2L, new Product(2L, "Imported Banana", 0.99, getRandomStock()));
        store.put(3L, new Product(3L, "Sunshine Rose Grape", 4.99, getRandomStock()));
        store.put(4L, new Product(4L, "Thai Golden Pillow Durian", 12.99, getRandomStock()));
        store.put(5L, new Product(5L, "Gannan Navel Orange", 1.49, getRandomStock()));
        store.put(6L, new Product(6L, "Hainan Mango", 2.99, getRandomStock()));
        store.put(7L, new Product(7L, "Zespri Kiwifruit", 3.49, getRandomStock()));
        store.put(8L, new Product(8L, "Hainan Coconut", 2.49, getRandomStock()));
        store.put(9L, new Product(9L, "Washington Red Cherry", 7.99, getRandomStock()));
        store.put(10L, new Product(10L, "Peruvian Blueberry", 4.99, getRandomStock()));
        store.put(11L, new Product(11L, "Australian Strawberry", 3.99, getRandomStock()));
        store.put(12L, new Product(12L, "Florida Orange", 1.79, getRandomStock()));
        store.put(13L, new Product(13L, "California Avocado", 3.29, getRandomStock()));
        store.put(14L, new Product(14L, "Chilean Grapefruit", 2.19, getRandomStock()));
        store.put(15L, new Product(15L, "Philippine Pineapple", 2.79, getRandomStock()));
        store.put(16L, new Product(16L, "Mexican Watermelon", 3.49, getRandomStock()));
        store.put(17L, new Product(17L, "Egyptian Pomegranate", 3.99, getRandomStock()));
        store.put(18L, new Product(18L, "Turkish Fig", 4.79, getRandomStock()));
        store.put(19L, new Product(19L, "Spanish Lemon", 1.19, getRandomStock()));
        store.put(20L, new Product(20L, "Italian Peach", 2.19, getRandomStock()));
        logger.info("Product repository initialized with {} products", store.size());
    }

    /**
     * Gets a random stock quantity
     * 
     * @return Random integer in the range [5, 14]
     */
    private int getRandomStock() {
        return new Random().nextInt(10) + 5;
    }

    /**
     * Query all products
     * 
     * @return Product collection
     */
    public Collection<Product> findAll() {
        logger.debug("Querying all products");
        Collection<Product> products = store.values();
        logger.info("Found {} products", products.size());
        return products;
    }

    /**
     * Query product by ID
     * 
     * @param id Product ID
     * @return Product object, or null if not found
     */
    public Product findById(Long id) {
        if (id == null) {
            logger.warn("Product ID is null when querying");
            return null;
        }
        logger.debug("Querying product by ID: {}", id);
        Product product = store.get(id);
        if (product != null) {
            logger.info("Found product: {}", product.getName());
        } else {
            logger.warn("Product not found with ID: {}", id);
        }
        return product;
    }
}