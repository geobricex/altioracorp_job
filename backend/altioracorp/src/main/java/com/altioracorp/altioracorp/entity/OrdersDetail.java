package com.altioracorp.altioracorp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "orders_details")
@Data
@NoArgsConstructor
@ToString
public class OrdersDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orders_id", nullable = false)
    @JsonIgnore
    private Order orders;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "products_id", nullable = false)
    private Product products;

//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Order getOrders() {
//        return orders;
//    }
//
//    public void setOrders(Order orders) {
//        this.orders = orders;
//    }
//
//    public Product getProducts() {
//        return products;
//    }
//
//    public void setProducts(Product products) {
//        this.products = products;
//    }

}
