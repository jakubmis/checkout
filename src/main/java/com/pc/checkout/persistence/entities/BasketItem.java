package com.pc.checkout.persistence.entities;

import com.pc.checkout.utils.Product;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by Mis on 2017-07-22.
 */
@Data
@Entity
@Table(name = "BASKET_ITEM")
public class BasketItem {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "AMOUNT", nullable = false)
    private Integer amount;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    public BasketItem(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public BasketItem() {
    }

    public void incrementAmountBy(int toIncrement) {
        amount += toIncrement;
    }

    public String getName() {
        return item.getName();
    }

    public Product toProduct() {
        return new Product(item.getName(), amount, countPriceWithoutPromotion(),
                amountOfPromotionGranted(), (countPriceWithoutPromotion() - countPrice()), countPrice());
    }

    public Integer countPrice() {
        return item.getPromotion() != null ? countPriceWithPromotion() : countPriceWithoutPromotion();
    }

    private Integer countPriceWithoutPromotion() {
        return (item.getPrice() * amount);
    }

    private Integer countPriceWithPromotion() {
        if (item.getPromotion() != null) {
            Integer amountForSpecialPrice = item.getPromotion().getAmount();
            int specialPriceAmount = amount / amountForSpecialPrice;
            int normalPriceAmount = amount - (specialPriceAmount * amountForSpecialPrice);
            return item.getPromotion().getSpecialPrice() * specialPriceAmount + normalPriceAmount * item.getPrice();
        } else {
            return 0;
        }
    }

    private Integer amountOfPromotionGranted() {
        if (item.getPromotion() != null) {
            Integer amountForSpecialPrice = item.getPromotion().getAmount();
            return amount / amountForSpecialPrice;
        } else {
            return 0;
        }
    }
}

