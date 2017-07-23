package com.pc.checkout.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Mis on 2017-07-23.
 */
@Data
@AllArgsConstructor
public class Product {

    private String name;
    private Integer amount;
    private Integer priceWithoutPromotion;
    private Integer numberOfGrantedPromotions;
    private Integer promotionRefund;
    private Integer finalPrice;
}
