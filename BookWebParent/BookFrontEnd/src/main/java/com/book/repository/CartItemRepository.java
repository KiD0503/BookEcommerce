package com.book.repository;

import com.book.common.entity.CartItem;
import com.book.common.entity.Customer;
import com.book.common.entity.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Integer> {
    public List<CartItem> findByCustomer(Customer customer);

    public CartItem findByCustomerAndProduct(Customer customer, Product product);

    @Modifying
    @Query("UPDATE CartItem c SET c.quantity = ?1 WHERE c.customer.id = ?2 AND c.product.id = ?3")
    public void updateQuantity(Integer quantity, Integer customerId, Integer productId);

    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.customer.id = ?1 AND c.product.id = ?2")
    public void deleteByCustomerAndProduct(Integer customerId, Integer productId);

    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.customer.id = ?1")
    public void deleteByCustomer(Integer customerId);
}