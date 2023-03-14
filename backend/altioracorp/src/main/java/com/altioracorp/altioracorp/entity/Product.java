package com.altioracorp.altioracorp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor

public class Product {
    public Product(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "code", nullable = false)
    private Integer code;

    @Column(name = "_name", nullable = false, length = 50)
    private String name;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "cant", nullable = false)
    private Integer cant;

    @Column(name = "state", nullable = false, length = 2)
    private String state;

    @OneToMany(mappedBy = "products")
    @JsonIgnore
    private List<OrdersDetail> ordersDetails;

}
