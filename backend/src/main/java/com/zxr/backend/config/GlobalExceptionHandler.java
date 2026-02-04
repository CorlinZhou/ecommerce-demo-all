package com.zxr.backend.config;

import com.zxr.backend.exception.InsufficientStockException;
import com.zxr.backend.exception.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Centralized exception handling for controllers.
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
      private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

      /**
       * Handle validation and parameter errors thrown by controllers or services.
       * <p>
       * Maps to HTTP 400 Bad Request and returns a JSON body with a single
       * <code>message</code> field describing the problem.
       * </p>
       *
       * @param ex the IllegalArgumentException thrown by application code
       * @return ResponseEntity&lt;Map&lt;String,String&gt;&gt; with status 400 and
       *         body { message }
       */
      @ExceptionHandler(IllegalArgumentException.class)
      public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException ex) {
            logger.warn("Bad request: {}", ex.getMessage());
            Map<String, String> body = new HashMap<>();
            body.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
      }

      /**
       * Handle cases where a requested resource (product) cannot be found.
       * <p>
       * Maps to HTTP 404 Not Found and returns a JSON body with a
       * <code>message</code> explaining which resource was missing.
       * </p>
       *
       * @param ex the ProductNotFoundException thrown by the service layer
       * @return ResponseEntity with status 404 and body { message }
       * @see com.zxr.backend.exception.ProductNotFoundException
       */
      @ExceptionHandler(ProductNotFoundException.class)
      public ResponseEntity<Map<String, String>> handleNotFound(ProductNotFoundException ex) {
            logger.warn("Not found: {}", ex.getMessage());
            Map<String, String> body = new HashMap<>();
            body.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
      }

      /**
       * Handle business conflicts such as insufficient stock for an order.
       * <p>
       * Maps to HTTP 409 Conflict and returns a JSON body with a
       * <code>message</code> describing the conflict.
       * </p>
       *
       * @param ex the InsufficientStockException thrown when stock is insufficient
       * @return ResponseEntity with status 409 and body { message }
       * @see com.zxr.backend.exception.InsufficientStockException
       */
      @ExceptionHandler(InsufficientStockException.class)
      public ResponseEntity<Map<String, String>> handleConflict(InsufficientStockException ex) {
            logger.warn("Conflict: {}", ex.getMessage());
            Map<String, String> body = new HashMap<>();
            body.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
      }

      /**
       * Catch-all handler for unexpected exceptions.
       * <p>
       * Logs the full exception for diagnostics and returns a generic
       * 500 response without leaking internal details to clients.
       * </p>
       *
       * @param ex the unexpected exception
       * @return ResponseEntity with status 500 and generic message
       */
      @ExceptionHandler(Exception.class)
      public ResponseEntity<Map<String, String>> handleInternal(Exception ex) {
            logger.error("Unhandled error: {}", ex.getMessage(), ex);
            Map<String, String> body = new HashMap<>();
            body.put("message", "Internal server error, please try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
      }
}
