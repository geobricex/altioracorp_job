package com.altioracorp.altioracorp.repository;

import com.altioracorp.altioracorp.entity.Customer;
import com.altioracorp.altioracorp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByState(String state);

}
