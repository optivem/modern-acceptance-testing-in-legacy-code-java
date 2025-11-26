// Common notification functions shared across all pages

import type { ApiError, ProblemDetail } from './types/order.types';

export function showNotification(
  message: string,
  isError: boolean = false,
  containerElementId: string = 'notifications'
): void {
  const notificationsDiv = document.getElementById(containerElementId);
  if (!notificationsDiv) {
    console.error(`Notification container not found: ${containerElementId}`);
    return;
  }

  notificationsDiv.innerHTML = '';

  const notif = document.createElement('div');
  notif.setAttribute('role', 'alert');
  notif.className = `notification ${isError ? 'error' : 'success'}`;
  notif.textContent = message;

  notificationsDiv.appendChild(notif);

  if (!isError) {
    setTimeout(() => {
      if (notif.parentNode) {
        notif.remove();
      }
    }, 5000);
  }
}

async function safeParseJson(response: Response): Promise<any> {
  try {
    return await response.json();
  } catch (e) {
    console.error('Error parsing JSON response:', e);
    return null;
  }
}

/**
 * Extracts error information from API response without showing notifications.
 * This is a pure function with no UI side effects.
 *
 * @param response The fetch Response object
 * @returns ApiError object with message and optional field errors
 */
export async function extractApiError(response: Response): Promise<ApiError> {
  const errorData: ProblemDetail | null = await safeParseJson(response);

  let message = '';
  let fieldErrors: string[] | undefined = undefined;

  if (errorData?.detail) {
    message = errorData.detail;

    if (errorData.errors && Array.isArray(errorData.errors) && errorData.errors.length > 0) {
      fieldErrors = errorData.errors.map(e => `${e.field}: ${e.message}`);
    }
  } else {
    message = `An unexpected error occurred. (Status: ${response.status})`;
  }

  return {
    message,
    fieldErrors,
    status: response.status
  };
}


