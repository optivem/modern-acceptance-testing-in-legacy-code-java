// Service layer for Order API operations

import { extractApiError } from '../common';
import type { PlaceOrderRequest, PlaceOrderResponse, GetOrderResponse, Result } from '../types/order.types';

class OrderService {
  private baseUrl: string;

  constructor(baseUrl: string = '/api/orders') {
    this.baseUrl = baseUrl;
  }

  async placeOrder(sku: string, quantity: number, country: string): Promise<Result<PlaceOrderResponse>> {
    try {
      const requestBody: PlaceOrderRequest = { sku, quantity, country };

      const response = await fetch(this.baseUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
      });

      if (response.ok) {
        const data = await response.json();
        return { success: true, data };
      }

      const error = await extractApiError(response);
      return { success: false, error };
    } catch (e: any) {
      return {
        success: false,
        error: {
          message: `Network error: ${e.message}`,
          status: 0
        }
      };
    }
  }

  async getOrder(orderNumber: string): Promise<Result<GetOrderResponse>> {
    try {
      const response = await fetch(`${this.baseUrl}/${orderNumber}`, {
        method: 'GET'
      });

      if (response.ok) {
        const data = await response.json();
        return { success: true, data };
      }

      const error = await extractApiError(response);
      return { success: false, error };
    } catch (e: any) {
      return {
        success: false,
        error: {
          message: `Network error: ${e.message}`,
          status: 0
        }
      };
    }
  }

  async cancelOrder(orderNumber: string): Promise<Result<void>> {
    try {
      const response = await fetch(`${this.baseUrl}/${orderNumber}/cancel`, {
        method: 'POST'
      });

      if (response.status === 204 || response.ok) {
        return { success: true, data: undefined };
      }

      const error = await extractApiError(response);
      return { success: false, error };
    } catch (e: any) {
      return {
        success: false,
        error: {
          message: `Network error: ${e.message}`,
          status: 0
        }
      };
    }
  }
}

export const orderService = new OrderService();

