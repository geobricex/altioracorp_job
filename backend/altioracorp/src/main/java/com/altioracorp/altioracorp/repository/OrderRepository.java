package com.altioracorp.altioracorp.repository;

import com.altioracorp.altioracorp.dto.OrderDto;
import com.altioracorp.altioracorp.entity.Customer;
import com.altioracorp.altioracorp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query(value = "SELECT o FROM Order o WHERE o.state = ?1", nativeQuery = false)
    List<Order> selectOrders(String state);


}
