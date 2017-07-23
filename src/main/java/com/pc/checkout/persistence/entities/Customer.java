package com.pc.checkout.persistence.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by jakub.mis on 7/20/2017.
 */
@Data
@Entity
@Table(name = "CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "BASKET_ID")
    private Basket basket;

    public Customer() {
        this.basket = new Basket();
    }

    public Customer(String name) {
        this.name = name;
        this.basket = new Basket();
    }

    public void clearBasket() {
        this.basket = new Basket();
    }
}
