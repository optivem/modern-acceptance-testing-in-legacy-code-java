// Service layer for Order API operations

import { handleApiResponse } from '../common';
import type { PlaceOrderRequest, PlaceOrderResponse, GetOrderResponse } from '../types/order.types';

class OrderService {
  private baseUrl: string;

  constructor(baseUrl: string = '/api/orders') {
    this.baseUrl = baseUrl;
  }

  async placeOrder(sku: string, quantity: number, country: string): Promise<PlaceOrderResponse> {
    const requestBody: PlaceOrderRequest = { sku, quantity, country };

    const response = await fetch(this.baseUrl, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(requestBody)
    });

    return await handleApiResponse(response) as PlaceOrderResponse;
  }

  async getOrder(orderNumber: string): Promise<GetOrderResponse> {
    const response = await fetch(`${this.baseUrl}/${orderNumber}`, {
      method: 'GET'
    });

    return await handleApiResponse(response) as GetOrderResponse;
  }

  async cancelOrder(orderNumber: string): Promise<{ success: boolean }> {
    const response = await fetch(`${this.baseUrl}/${orderNumber}/cancel`, {
      method: 'POST'
    });

    if (response.status === 204) {
      return { success: true };
    }

    await handleApiResponse(response);
    return { success: false };
  }
}

export const orderService = new OrderService();

