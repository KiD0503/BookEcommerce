package com.book.admin.user.service;

import com.book.admin.user.repository.ProductRepository;
import com.book.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> listAll(){
        return (List<Product>) productRepository.findAll();
    }
}
