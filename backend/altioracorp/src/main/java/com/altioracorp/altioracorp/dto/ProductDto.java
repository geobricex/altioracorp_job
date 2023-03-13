package com.altioracorp.altioracorp.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductDto implements Serializable {
    private  Integer id;
    private  Integer code;
    private  String name;
    private  BigDecimal unitPrice;
    private  String state;
}
