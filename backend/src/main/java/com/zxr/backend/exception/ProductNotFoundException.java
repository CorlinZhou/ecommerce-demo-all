package com.zxr.backend.exception;

/**
 * Thrown when a referenced product cannot be found.
 */
public class ProductNotFoundException extends RuntimeException {
      /**
       * Default constructor.
       */
      public ProductNotFoundException() {
            super();
      }

      /**
       * Construct with a message describing which product was not found.
       *
       * @param message human-readable message (used in API error body)
       */
      public ProductNotFoundException(String message) {
            super(message);
      }

      /**
       * Construct with message and cause.
       *
       * @param message human-readable message
       * @param cause   original cause
       */
      public ProductNotFoundException(String message, Throwable cause) {
            super(message, cause);
      }
}
