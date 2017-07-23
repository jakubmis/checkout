package com.pc.checkout.persistence.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by jakub.mis on 7/20/2017.
 */
@Entity
@Data
@Table(name = "ITEM")
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;
    @Column(name = "PRICE", nullable = false)
    private Integer price;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ITEM_ID")
    private ItemPromotion promotion;

    public Item() {
    }

    public Item(int price, String name) {
        this.price = price;
        this.name = name;
    }
}
