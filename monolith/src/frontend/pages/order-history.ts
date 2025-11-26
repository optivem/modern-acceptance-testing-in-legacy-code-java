// UI Controller for Order History page

import { showNotification } from '../common';
import { orderService } from '../services/order-service';
import type { GetOrderResponse } from '../types/order.types';

document.getElementById('searchForm')?.addEventListener('submit', async function(e: Event) {
  e.preventDefault();

  const orderNumberElement = document.getElementById('orderNumber') as HTMLInputElement;
  const orderNumber = orderNumberElement?.value ?? '';
  await displayOrderDetails(orderNumber);
});

async function displayOrderDetails(orderNumber: string): Promise<void> {
  const result = await orderService.getOrder(orderNumber);

  if (result.success) {
    renderOrderDetails(result.data);
  } else {
    const errorMessage = result.error.fieldErrors
      ? `${result.error.message}\n${result.error.fieldErrors.join('\n')}`
      : result.error.message;
    showNotification(errorMessage, true);
  }
}

function renderOrderDetails(order: GetOrderResponse): void {
  const template = document.getElementById('orderDetailsTemplate') as HTMLTemplateElement;
  if (!template) {
    console.error('Order details template not found');
    return;
  }

  const content = template.content.cloneNode(true) as DocumentFragment;

  // Populate fields with null-safe access
  const setValue = (id: string, value: string) => {
    const element = content.querySelector(`#${id}`) as HTMLInputElement;
    if (element) {
      element.value = value;
    }
  };

  setValue('displayOrderNumber', order.orderNumber);
  setValue('displaySku', order.sku);
  setValue('displayCountry', order.country);
  setValue('displayQuantity', order.quantity.toString());
  setValue('displayUnitPrice', `$${order.unitPrice.toFixed(2)}`);
  setValue('displayOriginalPrice', `$${order.originalPrice.toFixed(2)}`);
  setValue('displayDiscountRate', `${(order.discountRate * 100).toFixed(2)}%`);
  setValue('displayDiscountAmount', `$${order.discountAmount.toFixed(2)}`);
  setValue('displaySubtotalPrice', `$${order.subtotalPrice.toFixed(2)}`);
  setValue('displayTaxRate', `${(order.taxRate * 100).toFixed(2)}%`);
  setValue('displayTaxAmount', `$${order.taxAmount.toFixed(2)}`);
  setValue('displayTotalPrice', `$${order.totalPrice.toFixed(2)}`);
  setValue('displayStatus', order.status);

  const cancelBtn = content.querySelector('#cancelButton') as HTMLButtonElement;
  if (cancelBtn) {
    if (order.status === 'PLACED') {
      cancelBtn.addEventListener('click', () => handleCancelOrder(order.orderNumber));
    } else {
      cancelBtn.remove();
    }
  }

  const container = document.getElementById('orderDetails');
  if (container) {
    container.innerHTML = '';
    container.appendChild(content);
  }
}

async function handleCancelOrder(orderNumber: string): Promise<void> {
  const result = await orderService.cancelOrder(orderNumber);

  if (result.success) {
    showNotification('Order cancelled successfully!', false);
    await displayOrderDetails(orderNumber);
  } else {
    const errorMessage = result.error.fieldErrors
      ? `${result.error.message}\n${result.error.fieldErrors.join('\n')}`
      : result.error.message;
    showNotification(errorMessage, true);
  }
}

