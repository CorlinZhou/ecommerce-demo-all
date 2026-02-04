package com.zxr.backend.dto;

/**
 * Order creation response DTO
 * Used to return order creation results to the frontend
 */
public class CreateOrderResponse {
    
    /** Order ID */
    private String orderId;
    
    /** Order total price */
    private double totalPrice;

    /**
     * Construct order creation response
     * @param orderId Order ID
     * @param totalPrice Order total price
     */
    public CreateOrderResponse(String orderId, double totalPrice) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
    }

    /**
     * Get order ID
     * @return Order ID
     */
    public String getOrderId() { 
        return orderId; 
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
     * @return Order response information
     */
    @Override
    public String toString() {
        return "CreateOrderResponse{" +
                "orderId='" + orderId + "'" +
                ", totalPrice=" + totalPrice +
                '}';
    }
}