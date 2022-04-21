package com.book.controller;

import com.book.common.entity.Address;
import com.book.common.entity.CartItem;
import com.book.common.entity.Customer;
import com.book.other.CheckoutInfo;
import com.book.other.Utility;
import com.book.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;
    @Autowired private CustomerService customerService;
    @Autowired private AddressService addressService;
    @Autowired private ShoppingCartService cartService;
    @Autowired private OrderService orderService;


    @GetMapping("/checkout")
    public String showCheckoutPage(Model model, HttpServletRequest request) {
        Customer customer = getAuthenticatedCustomer(request);

        Address defaultAddress = addressService.getDefaultAddress(customer);

        if (defaultAddress != null) {
            model.addAttribute("shippingAddress", defaultAddress.toString());
        } else {
            model.addAttribute("shippingAddress", customer.toString());
        }


        List<CartItem> cartItems = cartService.listCartItems(customer);
        CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems);

        model.addAttribute("checkoutInfo", checkoutInfo);
        model.addAttribute("cartItems", cartItems);

        return "checkout/checkout";
    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request) {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        return customerService.getCustomerByEmail(email);
    }
    @PostMapping("/place_order")
    public String placeOrder(HttpServletRequest request) {

        Customer customer = getAuthenticatedCustomer(request);

        Address defaultAddress = addressService.getDefaultAddress(customer);

        List<CartItem> cartItems = cartService.listCartItems(customer);
        CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems);

        orderService.createOrder(customer, defaultAddress, cartItems, checkoutInfo);
        cartService.deleteByCustomer(customer);

        return "checkout/order_completed";
    }
}