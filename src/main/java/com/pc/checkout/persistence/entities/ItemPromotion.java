package com.pc.checkout.persistence.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Mis on 2017-07-22.
 */
@Entity
@Data
@Table(name = "ITEM_PROMOTION")
public class ItemPromotion {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    @Column(name = "AMOUNT")
    private Integer amount;
    @Column(name = "SPECIAL_PRICE")
    private Integer specialPrice;

    public ItemPromotion() {
    }

    public ItemPromotion(Integer amount, Integer specialPrice) {
        this.amount = amount;
        this.specialPrice = specialPrice;
    }
}
