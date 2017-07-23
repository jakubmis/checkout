package com.pc.checkout.utils;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Mis on 2017-07-23.
 */
public class ReceiptTest {

    private static final String RECEIPT_CONTENT = "RECEIPT_CONTENT";
    private static final Integer PRICE = 100;
    private static final Integer REBATE_GRANTED = 5;
    private Receipt receipt;

    @Before
    public void setUp() throws Exception {
        receipt = new Receipt(RECEIPT_CONTENT, PRICE, REBATE_GRANTED);
    }

    @Test
    public void testPrint() throws Exception {
        String print = receipt.print();
        assertNotNull(print);
        assertTrue(print.contains(PRICE.toString()));
        assertTrue(print.contains(REBATE_GRANTED.toString()));
    }

}