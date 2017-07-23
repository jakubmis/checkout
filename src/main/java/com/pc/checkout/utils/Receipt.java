package com.pc.checkout.utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Created by Mis on 2017-07-22.
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Receipt {

    private static final String NEW_LINE = "\n";

    private String content;
    private Integer price;
    private Integer rebateGranted;

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append("Receipt" + NEW_LINE);
        sb.append("------------" + NEW_LINE);
        sb.append(content + NEW_LINE);
        sb.append("------------" + NEW_LINE);
        sb.append("Price: " + price + NEW_LINE);
        sb.append("Rebate granted: " + rebateGranted + NEW_LINE);
        sb.append("Total price: " + (price - rebateGranted));
        return sb.toString();
    }
}
