// UI Controller for Place Order page

import { showNotification, handleApiCall } from '../common';
import { orderService } from '../services/order-service';
import type { OrderFormData } from '../types/order.types';

document.getElementById('orderForm')?.addEventListener('submit', async function(e: Event) {
  e.preventDefault();

  const orderData = collectFormData();

  if (!validateFormData(orderData)) {
    return;
  }

  await handleApiCall(async () => {
    const order = await orderService.placeOrder(orderData.sku, orderData.quantity, orderData.country);
    showNotification('Success! Order has been created with Order Number ' + order.orderNumber, false);
  });
});

function collectFormData(): OrderFormData {
  const skuElement = document.getElementById('sku') as HTMLInputElement;
  const quantityElement = document.getElementById('quantity') as HTMLInputElement;
  const countryElement = document.getElementById('country') as HTMLInputElement;

  const skuValue = skuElement?.value ?? '';
  const quantityValue = quantityElement?.value ?? '';
  const countryValue = countryElement?.value ?? '';

  return {
    sku: skuValue.trim(),
    quantity: parseInt(quantityValue),
    country: countryValue.trim(),
    quantityValue: quantityValue
  };
}

function validateFormData(data: OrderFormData): boolean {
  const notificationsDiv = document.getElementById('notifications');
  if (notificationsDiv) {
    notificationsDiv.innerHTML = '';
  }

  const quantityTrimmed = data.quantityValue.trim();

  if (!data.sku) {
    showNotification('SKU must not be empty', true);
    return false;
  }

  if (quantityTrimmed === '') {
    showNotification('Quantity must not be empty', true);
    return false;
  }

  const quantityNum = parseFloat(quantityTrimmed);

  if (isNaN(quantityNum)) {
    showNotification('Quantity must be an integer', true);
    return false;
  }

  if (!Number.isInteger(quantityNum)) {
    showNotification('Quantity must be an integer', true);
    return false;
  }

  if (quantityNum <= 0) {
    showNotification('Quantity must be positive', true);
    return false;
  }

  if (!data.country) {
    showNotification('Country must not be empty', true);
    return false;
  }

  return true;
}

