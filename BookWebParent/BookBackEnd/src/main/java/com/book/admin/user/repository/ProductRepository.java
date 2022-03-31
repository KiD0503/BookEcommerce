package com.book.admin.user.repository;

import com.book.common.entity.Product;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface ProductRepository extends PagingAndSortingRepository<Product,Integer> {

    public Product findByName(String name);
}
