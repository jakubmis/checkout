package com.pc.checkout.utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

/**
 * Created by Mis on 2017-07-22.
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Receipt {

    private List<Product> content;
    private Integer rawPrice;
    private Integer rebateGranted;
    private Integer finalPrice;
}
