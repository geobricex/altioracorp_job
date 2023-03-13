package com.altioracorp.altioracorp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
public class OrderDto implements Serializable {

    private Integer id;
    private CustomerDto customer;
    private Instant date;
    private String state;
    private List<OrdersDetailDto> ordersDetails;
}
