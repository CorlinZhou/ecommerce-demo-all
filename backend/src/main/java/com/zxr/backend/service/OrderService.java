package com.zxr.backend.service;

import com.zxr.backend.model.Order;
import com.zxr.backend.model.Product;
import com.zxr.backend.repository.OrderRepository;
import com.zxr.backend.repository.ProductRepository;
import com.zxr.backend.dto.CreateOrderResponse;
import com.zxr.backend.exception.ProductNotFoundException;
import com.zxr.backend.exception.InsufficientStockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Order service class
 * Responsible for handling order-related business logic
 */
@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private static final DateTimeFormatter ORDER_ID_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    /**
     * Construct order service
     * 
     * @param productRepository Product repository
     * @param orderRepository   Order repository
     */
    public OrderService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        logger.info("Order service initialized");
    }

    /**
     * Create order with single product
     * 
     * @param productId Product ID
     * @param quantity  Purchase quantity
     * @return Created order object
     * @throws IllegalArgumentException if parameters are invalid
     * @throws RuntimeException         if product not found or insufficient stock
     */
    public Order createOrder(Long productId, int quantity) {
        logger.info("Creating order, product ID: {}, quantity: {}", productId, quantity);

        // Validate order request
        validateOrderRequest(productId, quantity);

        // Query product
        Product product = productRepository.findById(productId);
        if (product == null) {
            logger.error("Order creation failed: Product ID[{}] not found", productId);
            throw new ProductNotFoundException("Product not found: " + productId);
        }

        // Check and deduct stock
        checkAndDeductStock(product, quantity);

        // Calculate total price
        double totalPrice = calculateTotalPrice(product, quantity);

        // Generate order ID
        String orderId = generateOrderId();

        // Create order object
        Order order = new Order(orderId, productId, quantity, totalPrice);

        // Save order
        orderRepository.save(order);

        logger.info("Order created successfully: Order ID[{}], Product name[{}], Total price[{}]",
                orderId, product.getName(), totalPrice);

        return order;
    }

    /**
     * Create order with multiple products
     * 
     * @param items List of order items with product ID and quantity
     * @return CreateOrderResponse containing orderId and totalPrice
     * @throws IllegalArgumentException   if parameters are invalid
     * @throws ProductNotFoundException   if a product is missing
     * @throws InsufficientStockException if stock is insufficient
     */
    public CreateOrderResponse createOrderWithItems(List<Map<String, Object>> items) {
        logger.info("Creating order with multiple items: {}", items);

        // Validate items
        if (items == null || items.isEmpty()) {
            logger.error("Order creation failed: No items in request");
            throw new IllegalArgumentException("No items in order request");
        }

        double totalPrice = 0.0;
        List<Order> orders = new ArrayList<>();
        String orderId = generateOrderId();

        // Process each item
        for (Map<String, Object> item : items) {
            // Get product ID and quantity from map (defensive parsing)
            Object pidObj = item.get("productId");
            Object qtyObj = item.get("quantity");
            if (pidObj == null || qtyObj == null) {
                throw new IllegalArgumentException("Each item must contain productId and quantity");
            }
            Long productId;
            int quantity;
            try {
                if (pidObj instanceof Number)
                    productId = ((Number) pidObj).longValue();
                else
                    productId = Long.valueOf(pidObj.toString());

                if (qtyObj instanceof Number)
                    quantity = ((Number) qtyObj).intValue();
                else
                    quantity = Integer.valueOf(qtyObj.toString());
            } catch (NumberFormatException | NullPointerException e) {
                throw new IllegalArgumentException("Invalid productId or quantity format", e);
            }

            // Validate each item
            validateOrderRequest(productId, quantity);

            // Query product
            Product product = productRepository.findById(productId);
            if (product == null) {
                logger.error("Order creation failed: Product ID[{}] not found", productId);
                throw new ProductNotFoundException("Product not found: " + productId);
            }

            // Check and deduct stock
            checkAndDeductStock(product, quantity);

            // Calculate item price and add to total
            double itemPrice = calculateTotalPrice(product, quantity);
            totalPrice += itemPrice;

            // Create order for each item (or use single order with items)
            Order order = new Order(orderId, productId, quantity, itemPrice);
            orders.add(order);
        }

        // Persist orders; rely on transactional semantics when using a DB-backed repository
        for (Order order : orders) {
            orderRepository.save(order);
        }

        // Round total price to 2 decimals for consistency with API contract
        double roundedTotal = BigDecimal.valueOf(totalPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
        logger.info("Multi-item order created successfully: Order ID[{}], Total price[{}]",
                orderId, roundedTotal);

        return new CreateOrderResponse(orderId, roundedTotal);
    }

    /**
     * Validate order request parameters
     * 
     * @param productId Product ID
     * @param quantity  Purchase quantity
     */
    private void validateOrderRequest(Long productId, int quantity) {
        if (productId == null || productId <= 0) {
            logger.error("Order creation failed: Invalid product ID: {}", productId);
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
        if (quantity <= 0) {
            logger.error("Order creation failed: Invalid quantity: {}", quantity);
            throw new IllegalArgumentException("Purchase quantity must be greater than 0");
        }
        logger.debug("Order request parameters validated successfully: Product ID[{}], Quantity[{}]", productId,
                quantity);
    }

    /**
     * Check stock and deduct
     * 
     * @param product  Product object
     * @param quantity Purchase quantity
     */
    private void checkAndDeductStock(Product product, int quantity) {
        logger.debug("Checking stock for product[{}]: Current stock[{}], Requested quantity[{}]",
                product.getName(), product.getStock(), quantity);

        // Use synchronized lock to ensure thread safety for stock deduction
        synchronized (product) {
            if (product.getStock() < quantity) {
                logger.error(
                        "Order creation failed: Insufficient stock for product[{}], Current stock[{}], Requested quantity[{}]",
                        product.getName(), product.getStock(), quantity);
                throw new InsufficientStockException(
                        "Insufficient stock for product: " + product.getName());
            }

            // Deduct stock
            int newStock = product.getStock() - quantity;
            product.setStock(newStock);
            logger.info("Stock deducted successfully for product[{}]: Original stock[{}], Deducted[{}], New stock[{}]",
                    product.getName(), product.getStock() + quantity, quantity, newStock);
        }
    }

    /**
     * Calculate order total price
     * 
     * @param product  Product object
     * @param quantity Purchase quantity
     * @return Order total price
     */
    private double calculateTotalPrice(Product product, int quantity) {
        // Use BigDecimal for precise decimal arithmetic
        BigDecimal price = BigDecimal.valueOf(product.getPrice());
        BigDecimal qty = BigDecimal.valueOf(quantity);
        BigDecimal totalPrice = price.multiply(qty).setScale(2, RoundingMode.HALF_UP);

        logger.debug("Calculating order total price: Product price[{}] * Quantity[{}] = {}",
                product.getPrice(), quantity, totalPrice);
        return totalPrice.doubleValue();
    }

    /**
     * Generate unique order ID
     * 
     * @return Order ID
     */
    private String generateOrderId() {
        // Generate order ID using timestamp + short random suffix to reduce collision
        // risk
        String orderId = "ORD-" + LocalDateTime.now().format(ORDER_ID_FORMATTER) + "-"
                + ThreadLocalRandom.current().nextInt(1000, 10000);
        logger.debug("Generated order ID: {}", orderId);
        return orderId;
    }
}