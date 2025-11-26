// Common notification functions shared across all pages

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

interface HandleApiResponseOptions {
  onSuccess?: (data: unknown) => unknown;
}

export async function handleApiResponse(
  response: Response,
  options: HandleApiResponseOptions = {}
): Promise<unknown> {
  const { onSuccess } = options;

  if (response.ok) {
    const data = await response.json();
    if (onSuccess) {
      return onSuccess(data);
    }
    return data;
  }

  const errorData = await safeParseJson(response);
  let displayMessage = '';

  if (errorData?.detail) {
    displayMessage = errorData.detail;

    if (errorData.errors && Array.isArray(errorData.errors) && errorData.errors.length > 0) {
      const fieldErrors = errorData.errors
        .map((e: { field: string; message: string }) => `${e.field}: ${e.message}`)
        .join('\n');
      displayMessage += '\n' + fieldErrors;
    }
  } else {
    displayMessage = `An unexpected error occurred. (Status: ${response.status})`;
  }

  showNotification(displayMessage, true);
  const error = new Error(displayMessage);
  (error as any).alreadyHandled = true;
  throw error;
}

async function safeParseJson(response: Response): Promise<any> {
  try {
    return await response.json();
  } catch (e) {
    console.error('Error parsing JSON response:', e);
    return null;
  }
}

export async function handleApiCall<T>(
  apiCallFn: () => Promise<T>,
  errorMessage: string = 'An error occurred. Please try again.'
): Promise<T> {
  try {
    return await apiCallFn();
  } catch (error: any) {
    // If the error already has a notification shown (from handleApiResponse), just re-throw it
    if (error.message && (error.message.includes('Status:') || error.alreadyHandled)) {
      throw error;
    }
    console.error('Exception during API call:', error);
    showNotification(`${errorMessage} (Exception: ${error.message})`, true);
    throw error;
  }
}

