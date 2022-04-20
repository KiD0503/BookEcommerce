package com.book.service;

import com.book.common.entity.CartItem;
import com.book.common.entity.Product;
import com.book.other.CheckoutInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckoutService {
    private static final int DIM_DIVISOR = 139;

    public CheckoutInfo prepareCheckout(List<CartItem> cartItems) {
        CheckoutInfo checkoutInfo = new CheckoutInfo();

        float productCost = calculateProductCost(cartItems);
        float productTotal = calculateProductTotal(cartItems);
        float paymentTotal = productTotal;

        checkoutInfo.setProductCost(productCost);
        checkoutInfo.setProductTotal(productTotal);
        checkoutInfo.setPaymentTotal(paymentTotal);

        return checkoutInfo;
    }

    private float calculateProductTotal(List<CartItem> cartItems) {
        float total = 0.0f;

        for (CartItem item : cartItems) {
            total += item.getSubtotal();
        }

        return total;
    }

    private float calculateProductCost(List<CartItem> cartItems) {
        float cost = 0.0f;

        for (CartItem item : cartItems) {
            cost += item.getQuantity() * item.getProduct().getCost();
        }

        return cost;
    }
}
