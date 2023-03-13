package com.altioracorp.altioracorp.service;

import com.altioracorp.altioracorp.entity.Order;
import com.altioracorp.altioracorp.entity.OrdersDetail;
import com.altioracorp.altioracorp.repository.OrderRepository;
import com.altioracorp.altioracorp.repository.OrdersDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrdersDetailsService {
    @Autowired
    private OrdersDetailRepository ordersDetailRepository;

    public List<OrdersDetail> getOrdersDetails() {
        return ordersDetailRepository.findAll();
    }
}
