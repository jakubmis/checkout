package com.pc.checkout.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by jakub.mis on 7/20/2017.
 */
@Data
@AllArgsConstructor
public class Price {

    private Integer amount;
    private String currency;
}