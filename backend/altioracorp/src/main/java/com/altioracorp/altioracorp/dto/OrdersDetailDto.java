package com.altioracorp.altioracorp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrdersDetailDto implements Serializable {
    private Long id;
    private ProductDto products;
    private Integer cantProduct;
}
