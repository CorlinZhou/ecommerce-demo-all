// Product types
export interface Product {
  id: number;
  name: string;
  price: number;
  stock: number;
}

// Order types
export interface CreateOrderRequest {
  productId: number;
  quantity: number;
}

export interface Order {
  id: string;
  productId: number;
  quantity: number;
  totalPrice: number;
  status: 'pending' | 'processing' | 'completed' | 'cancelled';
  createdAt: string;
  updatedAt: string;
}

export interface CreateOrderResponse {
  orderId: string;
  totalPrice: number;
  status: 'pending';
  createdAt: string;
}

// API response types
export interface ApiResponse<T = any> {
  code: number;
  message: string;
  data: T;
}

export interface PaginationParams {
  page?: number;
  pageSize?: number;
}

export interface PaginatedResponse<T> {
  list: T[];
  total: number;
  page: number;
  pageSize: number;
}

// Order item and draft types
export interface OrderItem {
  productId: number;
  name: string;
  price: number;
  quantity: number;
}

export interface OrderConfirmation {
  orderId: string;
  totalPrice: number;
}

export type OrderDraft = Record<number, OrderItem>
