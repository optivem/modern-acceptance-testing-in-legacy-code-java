package com.optivem.eshop.systemtest.core.drivers.system;

import java.util.Optional;

public interface ShopDriver extends AutoCloseable {
    void goToShop();

    Result<String> placeOrder(String sku, String quantity, String country);

    void confirmOrderDetails(String orderNumber, Optional<String> sku, Optional<String> quantity, Optional<String> country,
                             Optional<String> unitPrice, Optional<String> originalPrice,  Optional<String> status);

    void confirmOrderNumberGeneratedWithPrefix(String orderNumber, String expectedPrefix);


    void confirmOrderPlaced(String orderNumber, String prefix);

    void cancelOrder(String orderNumber);

    void confirmOrderCancelled(String orderNumber);

    void confirmOrderStatusIsCancelled(String orderNumber);

    void confirmSubtotalPricePositive(String orderNumber);

    void confirmTotalPricePositive(String orderNumber);
}
