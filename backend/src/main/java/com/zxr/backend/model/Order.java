package com.zxr.backend.model;

/**
 * Order model class
 * Represents order information in the system
 */
public class Order {

    /** Order ID */
    private final String id;
    
    /** Product ID */
    private final Long productId;
    
    /** Purchase quantity */
    private final int quantity;
    
    /** Order total price */
    private final double totalPrice;

    /**
     * Construct order object
     * @param id Order ID
     * @param productId Product ID
     * @param quantity Purchase quantity
     * @param totalPrice Order total price
     * @throws IllegalArgumentException if parameters are invalid
     */
    public Order(String id, Long productId, int quantity, double totalPrice) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty");
        }
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Purchase quantity must be greater than 0");
        }
        if (totalPrice < 0) {
            throw new IllegalArgumentException("Order total price cannot be negative");
        }
        
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    /**
     * Get order ID
     * @return Order ID
     */
    public String getId() { 
        return id; 
    }
    
    /**
     * Get product ID
     * @return Product ID
     */
    public Long getProductId() { 
        return productId; 
    }
    
    /**
     * Get purchase quantity
     * @return Purchase quantity
     */
    public int getQuantity() { 
        return quantity; 
    }
    
    /**
     * Get order total price
     * @return Order total price
     */
    public double getTotalPrice() { 
        return totalPrice; 
    }
    
    /**
     * Override toString method for logging purposes
     * @return Order information
     */
    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}