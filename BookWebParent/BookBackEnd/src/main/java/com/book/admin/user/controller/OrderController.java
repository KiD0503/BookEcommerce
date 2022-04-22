package com.book.admin.user.controller;

import com.book.admin.user.paging.PagingAndSortingHelper;
import com.book.admin.user.paging.PagingAndSortingParam;
import com.book.admin.user.service.OrderService;
import com.book.admin.user.service.SettingService;
import com.book.common.entity.Order;
import com.book.common.entity.OrderDetail;
import com.book.common.entity.Product;
import com.book.common.entity.Setting;
import com.book.common.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

@Controller
public class OrderController {
    private String defaultRedirectURL = "redirect:/orders/page/1?sortField=orderTime&sortDir=desc";

    @Autowired
    private OrderService orderService;
    @Autowired private SettingService settingService;

    @GetMapping("/orders")
    public String listFirstPage() {
        return defaultRedirectURL;
    }

    @GetMapping("/orders/page/{pageNum}")
    public String listByPage(
            @PagingAndSortingParam(listName = "listOrders", moduleURL = "/orders") PagingAndSortingHelper helper,
            @PathVariable(name = "pageNum") int pageNum,
            HttpServletRequest request) {

        orderService.listByPage(pageNum, helper);
        loadCurrencySetting(request);

        return "orders/orders";
    }

    private void loadCurrencySetting(HttpServletRequest request) {
        List<Setting> currencySettings = settingService.getCurrencySettings();

        for (Setting setting : currencySettings) {
            request.setAttribute(setting.getKey(), setting.getValue());
        }
    }

    @GetMapping("/orders/detail/{id}")
    public String viewOrderDetails(@PathVariable("id") Integer id, Model model,
                                   RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            Order order = orderService.get(id);
            loadCurrencySetting(request);
            model.addAttribute("order", order);

            return "orders/order_details_modal";
        } catch (OrderNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return defaultRedirectURL;
        }

    }

    @GetMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            orderService.delete(id);;
            ra.addFlashAttribute("message", "The order ID " + id + " has been deleted.");
        } catch (OrderNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
        }

        return defaultRedirectURL;
    }

    @GetMapping("/orders/edit/{id}")
    public String editOrder(@PathVariable("id") Integer id, Model model, RedirectAttributes ra,
                            HttpServletRequest request) {
        try {
            Order order = orderService.get(id);;

            model.addAttribute("pageTitle", "Edit Order (ID: " + id + ")");
            model.addAttribute("order", order);

            return "orders/order_form";

        } catch (OrderNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
            return defaultRedirectURL;
        }

    }

    @PostMapping("/order/save")
    public String saveOrder(Order order, HttpServletRequest request, RedirectAttributes ra) {
        updateProductDetails(order, request);
        orderService.save(order);

        ra.addFlashAttribute("message", "The order ID " + order.getId() + " has been updated successfully");

        return defaultRedirectURL;
    }


    private void updateProductDetails(Order order, HttpServletRequest request) {
        String[] detailIds = request.getParameterValues("detailId");
        String[] productIds = request.getParameterValues("productId");
        String[] productPrices = request.getParameterValues("productPrice");
        String[] productDetailCosts = request.getParameterValues("productDetailCost");
        String[] quantities = request.getParameterValues("quantity");
        String[] productSubtotals = request.getParameterValues("productSubtotal");
        Set<OrderDetail> orderDetails = order.getOrderDetails();

        for (int i = 0; i < detailIds.length; i++) {
            System.out.println("Detail ID: " + detailIds[i]);
            System.out.println("\t Prodouct ID: " + productIds[i]);
            System.out.println("\t Cost: " + productDetailCosts[i]);
            System.out.println("\t Quantity: " + quantities[i]);
            System.out.println("\t Subtotal: " + productSubtotals[i]);

            OrderDetail orderDetail = new OrderDetail();
            Integer detailId = Integer.parseInt(detailIds[i]);
            if (detailId > 0) {
                orderDetail.setId(detailId);
            }

            orderDetail.setOrder(order);
            orderDetail.setProduct(new Product(Integer.parseInt(productIds[i])));
            orderDetail.setProductCost(Float.parseFloat(productDetailCosts[i]));
            orderDetail.setSubtotal(Float.parseFloat(productSubtotals[i]));
            orderDetail.setQuantity(Integer.parseInt(quantities[i]));
            orderDetail.setUnitPrice(Float.parseFloat(productPrices[i]));

            orderDetails.add(orderDetail);

        }

    }
}