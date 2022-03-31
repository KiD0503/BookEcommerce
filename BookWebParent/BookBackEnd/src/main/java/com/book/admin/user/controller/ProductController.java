package com.book.admin.user.controller;

import com.book.admin.exception.ProductNotFoundException;
import com.book.admin.user.service.CategoryService;
import com.book.admin.user.service.ProductService;
import com.book.common.entity.Category;
import com.book.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public String listAll(Model model){
        List<Product> listProducts = productService.listAll();
        model.addAttribute("listProducts", listProducts);
        return "products/products";
    }

    @GetMapping("/products/new")
    public String newProduct(Model model){
        List<Category> listCategories = categoryService.listCategoriesUsedInForm();
        Product product = new Product();
        product.setEnabled(true);
        product.setInStock(true);

        model.addAttribute("product", product);
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("pageTitle", "Create New Product");
        return "products/product_form";
    }
    @PostMapping("/products/save")
    public String saveProduct(Product product, RedirectAttributes redirectAttributes) {
        productService.save(product);
        redirectAttributes.addFlashAttribute("message", "The product has been saved successfully.");

        return "redirect:/products";
    }

    @GetMapping("/products/{id}/enabled/{status}")
    public String updateCategoryEnabledStatus(@PathVariable("id") Integer id,
                                              @PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
        productService.updateProductEnabledStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The Product ID " + id + " has been " + status;
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Integer id,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        try {
            productService.delete(id);

            redirectAttributes.addFlashAttribute("message",
                    "The product ID " + id + " has been deleted successfully");
        } catch (ProductNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/products";
    }

}
