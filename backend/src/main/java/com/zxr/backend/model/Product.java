package com.zxr.backend.model;

/**
 * Product model class
 * Represents product information in the system
 */
public class Product {

    /** Product ID */
    private final Long id;
    
    /** Product name */
    private final String name;
    
    /** Product price */
    private final double price;
    
    /** Product stock */
    private int stock;

    /**
     * Construct product object
     * @param id Product ID
     * @param name Product name
     * @param price Product price
     * @param stock Product stock
     * @throws IllegalArgumentException if parameters are invalid
     */
    public Product(Long id, String name, double price, int stock) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("Product stock cannot be negative");
        }
        
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * Get product ID
     * @return Product ID
     */
    public Long getId() { 
        return id; 
    }
    
    /**
     * Get product name
     * @return Product name
     */
    public String getName() { 
        return name; 
    }
    
    /**
     * Get product price
     * @return Product price
     */
    public double getPrice() { 
        return price; 
    }
    
    /**
     * Get product stock
     * @return Product stock
     */
    public int getStock() { 
        return stock; 
    }
    
    /**
     * Set product stock
     * @param stock Product stock
     * @throws IllegalArgumentException if stock is negative
     */
    public void setStock(int stock) { 
        if (stock < 0) {
            throw new IllegalArgumentException("Product stock cannot be negative");
        }
        this.stock = stock; 
    }
    
    /**
     * Override toString method for logging purposes
     * @return Product information
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}