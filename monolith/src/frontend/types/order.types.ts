// Domain types
export interface Order {
  orderNumber: string;
  sku: string;
  country: string;
  quantity: number;
  unitPrice: number;
  originalPrice: number;
  discountRate: number;
  discountAmount: number;
  subtotalPrice: number;
  taxRate: number;
  taxAmount: number;
  totalPrice: number;
  status: OrderStatus;
}

export enum OrderStatus {
  PLACED = 'PLACED',
  CANCELLED = 'CANCELLED'
}

// API Request types
export interface PlaceOrderRequest {
  sku: string;
  quantity: number;
  country: string;
}

// API Response types
export interface PlaceOrderResponse {
  orderNumber: string;
}

export interface GetOrderResponse extends Order {}

// Validation types
export interface ValidationError {
  field: string;
  message: string;
}

export interface ProblemDetail {
  type?: string;
  title?: string;
  status: number;
  detail?: string;
  errors?: ValidationError[];
  timestamp?: string;
}

// Form data types
export interface OrderFormData {
  sku: string;
  quantity: number;
  country: string;
  quantityValue: string;
}

