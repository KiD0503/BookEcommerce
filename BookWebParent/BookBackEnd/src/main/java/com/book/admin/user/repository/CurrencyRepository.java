package com.book.admin.user.repository;

import com.book.common.entity.Currency;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface CurrencyRepository extends CrudRepository<Currency, CriteriaBuilder.In> {
}
