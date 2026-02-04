package com.zxr.backend.controller;

import com.zxr.backend.dto.CreateOrderRequest;
import com.zxr.backend.service.OrderService;
import com.zxr.backend.dto.CreateOrderResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Order controller class
 * Handles order-related HTTP requests
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService service;

    /**
     * Construct order controller
     * 
     * @param service Order service
     */
    public OrderController(OrderService service) {
        this.service = service;
        logger.info("Order controller initialized");
    }

    /**
     * Create order with multiple products
     * 
     * @param request Order creation request
     * @return Order creation response
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        logger.info("Received order creation request: {}", request);

        // Basic input validation kept at controller level for fast-fail
        if (request.getItems() == null || request.getItems().isEmpty()) {
            logger.error("Order creation failed: No items in request");
            return ResponseEntity.badRequest().build();
        }

        // Convert items to map format expected by service
        List<Map<String, Object>> items = new ArrayList<>();
        for (CreateOrderRequest.Item item : request.getItems()) {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("productId", item.getProductId());
            itemMap.put("quantity", item.getQuantity());
            items.add(itemMap);
        }

        // Delegate full business logic to service (price calculation, stock,
        // persistence)
        CreateOrderResponse svcResp = service.createOrderWithItems(items);

        // Build API response (keeps previous shape expected by frontend)
        Map<String, Object> response = new HashMap<>();
        response.put("orderId", svcResp.getOrderId());
        response.put("totalPrice", svcResp.getTotalPrice());
        response.put("status", "pending");
        response.put("createdAt", new java.util.Date());

        logger.info("Order created successfully, response: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
