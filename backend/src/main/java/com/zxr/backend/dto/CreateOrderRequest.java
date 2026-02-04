package com.zxr.backend.dto;

import java.util.List;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Order creation request DTO
 * Used to receive order creation parameters from the frontend
 */
public class CreateOrderRequest {

    @NotEmpty(message = "items must not be empty")
    private List<Item> items;

    /**
     * Get order items
     * 
     * @return List of order items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Set order items
     * 
     * @param items List of order items
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item {
        @NotNull(message = "productId is required")
        @Min(value = 1, message = "productId must be a positive number")
        private Long productId;

        @NotNull(message = "quantity is required")
        @Min(value = 1, message = "quantity must be at least 1")
        private Integer quantity;

        /**
         * Get product ID
         * 
         * @return Product ID
         */
        public Long getProductId() {
            return productId;
        }

        /**
         * Set product ID
         * 
         * @param productId Product ID
         */
        public void setProductId(Long productId) {
            this.productId = productId;
        }

        /**
         * Get quantity
         * 
         * @return Quantity
         */
        public Integer getQuantity() {
            return quantity;
        }

        /**
         * Set quantity
         * 
         * @param quantity Quantity
         */
        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}