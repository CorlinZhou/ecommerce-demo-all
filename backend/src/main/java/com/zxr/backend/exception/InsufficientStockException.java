package com.zxr.backend.exception;

/**
 * Thrown when an order cannot be fulfilled due to insufficient stock.
 */
public class InsufficientStockException extends RuntimeException {
      /**
       * Default constructor.
       */
      public InsufficientStockException() {
            super();
      }

      /**
       * Construct with a message describing the stock conflict.
       *
       * @param message human-readable message (used in API error body)
       */
      public InsufficientStockException(String message) {
            super(message);
      }

      /**
       * Construct with message and cause.
       *
       * @param message human-readable message
       * @param cause   original cause
       */
      public InsufficientStockException(String message, Throwable cause) {
            super(message, cause);
      }
}
