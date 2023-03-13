package com.altioracorp.altioracorp.controller;

import com.altioracorp.altioracorp.entity.OrdersDetail;
import com.altioracorp.altioracorp.service.OrdersDetailsService;
import com.altioracorp.altioracorp.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/ordersdetails")
public class OrdersDetailsApi {
    @Autowired
    private OrdersDetailsService ordersDetailService;

    @GetMapping
    public ResponseEntity<?> getOrdensDetails() {
        return ResponseEntity.ok(ordersDetailService.getOrdersDetails());
    }
}
