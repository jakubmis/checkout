package com.pc.checkout.persistence.entities;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by Mis on 2017-07-23.
 */
public class CustomerTest {

    private Customer customer;

    @Before
    public void setUp() throws Exception {
        customer = new Customer();
        Basket basket = new Basket();
        basket.put(new Item(), 1);
        customer.setBasket(basket);
    }

    @Test
    public void testClearBasket() throws Exception {
        Basket basket = customer.getBasket();
        customer.clearBasket();
        assertNotEquals(basket, customer.getBasket());
    }

}