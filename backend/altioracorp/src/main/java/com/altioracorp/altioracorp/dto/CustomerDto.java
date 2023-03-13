package com.altioracorp.altioracorp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerDto implements Serializable {
    private  Long id;
    private  String dni;
    private  String name;
    private  String lastname;
    private  String state;
}
