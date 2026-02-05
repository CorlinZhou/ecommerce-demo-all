package com.zxr.backend.service;

import com.zxr.backend.dto.CreateOrderResponse;
import com.zxr.backend.exception.InsufficientStockException;
import com.zxr.backend.model.Product;
import com.zxr.backend.repository.OrderRepository;
import com.zxr.backend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

      @Mock
      ProductRepository productRepository;

      @Mock
      OrderRepository orderRepository;

      @InjectMocks
      OrderService orderService;

      Product p1;
      Product p2;

      @BeforeEach
      void setUp() {
            p1 = new Product(1L, "P1", 2.00, 5);
            p2 = new Product(2L, "P2", 3.00, 3);
      }

      @Test
      void createOrderWithItems_success() {
            when(productRepository.findById(1L)).thenReturn(p1);
            when(productRepository.findById(2L)).thenReturn(p2);

            CreateOrderResponse resp = orderService.createOrderWithItems(List.of(
                        Map.of("productId", 1L, "quantity", 2),
                        Map.of("productId", 2L, "quantity", 1)));

            assertEquals("ORD-", resp.getOrderId().substring(0, 4));
            assertEquals(7.00, resp.getTotalPrice(), 0.001);
            org.mockito.Mockito.verify(orderRepository, org.mockito.Mockito.times(2)).save(any());
            assertEquals(3, p1.getStock());
            assertEquals(2, p2.getStock());
      }

      @Test
      void createOrderWithItems_invalidPayload_missingField() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                        () -> orderService.createOrderWithItems(List.of(Map.of("quantity", 1))));
            assertTrue(ex.getMessage().contains("productId"));
      }

      @Test
      void createOrderWithItems_insufficientStock() {
            when(productRepository.findById(1L)).thenReturn(new Product(1L, "P1", 2.0, 1));

            assertThrows(InsufficientStockException.class,
                        () -> orderService.createOrderWithItems(List.of(Map.of("productId", 1L, "quantity", 2))));

            // ensure no order persisted and stock unchanged
            verify(orderRepository, org.mockito.Mockito.never()).save(any());
      }
}
