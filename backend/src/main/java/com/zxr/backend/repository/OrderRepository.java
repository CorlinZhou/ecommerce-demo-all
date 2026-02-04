package com.zxr.backend.repository;

import com.zxr.backend.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Order repository class
 * Responsible for order data storage and access
 */
@Repository
public class OrderRepository {

    private static final Logger logger = LoggerFactory.getLogger(OrderRepository.class);
    
    /** Order storage container */
    private final Map<String, Order> store = new ConcurrentHashMap<>();

    /**
     * Save order
     * @param order Order object
     * @throws IllegalArgumentException if order object is null
     */
    public void save(Order order) {
        if (order == null) {
            logger.error("Order object is null when saving");
            throw new IllegalArgumentException("Order object cannot be null");
        }
        logger.debug("Saving order: {}", order.getId());
        store.put(order.getId(), order);
        logger.info("Order saved successfully: {}", order.getId());
    }

    /**
     * Query order by ID
     * @param id Order ID
     * @return Order object, or null if not found
     */
    public Order findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            logger.warn("Order ID is empty when querying");
            return null;
        }
        logger.debug("Querying order by ID: {}", id);
        Order order = store.get(id);
        if (order != null) {
            logger.info("Found order: {}", order.getId());
        } else {
            logger.warn("Order not found with ID: {}", id);
        }
        return order;
    }
}