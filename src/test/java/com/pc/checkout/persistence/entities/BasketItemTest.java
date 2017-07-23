package com.pc.checkout.persistence.entities;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Mis on 2017-07-23.
 */
public class BasketItemTest {

    private static final String PRODUCT_TAG = "|Product: ";
    private static final String AMOUNT_TAG = "|Amount: ";
    private static final String PRICE_TAG = "|Price: ";
    private static final String AMOUNT_OF_PROMOTION_TAG = "|AmountOfPromotion: ";
    private static final String PROMOTION_TAG = "|Promotion: ";
    private static final String TOTAL_TAG = "|Total: ";
    private static final Integer PROMOTION_PRICE = 10;
    private static final Integer PROMOTION_AMOUNT = 5;
    private BasketItem basketItem;

    @Before
    public void setUp() throws Exception {
        basketItem = new BasketItem();
        basketItem.setAmount(0);
    }

    @Test
    public void testIncrementAmountBy() throws Exception {
        basketItem.incrementAmountBy(5);
        assertEquals(basketItem.getAmount(), new Integer(5));
    }

    @Test
    public void testPrint() throws Exception {
        preparePromotionBasketItem("A", 6, 5);

        String print = basketItem.print();

        assertNotNull(print);
        assertTrue(print.contains(PRODUCT_TAG + basketItem.getName()));
        assertTrue(print.contains(AMOUNT_TAG + basketItem.getAmount()));
        assertTrue(print.contains(PRICE_TAG + 30));
        assertTrue(print.contains(AMOUNT_OF_PROMOTION_TAG + 1));
        assertTrue(print.contains(PROMOTION_TAG + 15));
        assertTrue(print.contains(TOTAL_TAG + 15));

    }

    private void preparePromotionBasketItem(String name, Integer amount, Integer price) {
        basketItem.setItem(preparePromotionItem(name, price));
        basketItem.setAmount(amount);
    }

    private Item preparePromotionItem(String name, Integer price) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setPromotion(new ItemPromotion(PROMOTION_AMOUNT, PROMOTION_PRICE));
        return item;
    }

    private void prepareBasketItem(String name, Integer price, Integer amount) {
        basketItem.setItem(new Item(price, name));
        basketItem.setAmount(amount);
    }

    @Test
    public void testPrintWithoutPromotion() throws Exception {
        prepareBasketItem("A", 5, 6);

        String print = basketItem.print();

        assertNotNull(print);
        assertTrue(print.contains(PRODUCT_TAG + basketItem.getName()));
        assertTrue(print.contains(AMOUNT_TAG + basketItem.getAmount()));
        assertTrue(print.contains(PRICE_TAG + 30));
        assertTrue(print.contains(AMOUNT_OF_PROMOTION_TAG + 0));
        assertTrue(print.contains(PROMOTION_TAG + 0));
        assertTrue(print.contains(TOTAL_TAG + 30));

    }

    @Test
    public void testGetName() throws Exception {
        Item item = new Item();
        item.setName("A");
        basketItem.setItem(item);

        assertEquals(basketItem.getName(), item.getName());
    }

    @Test
    public void testCountPriceWithPromotion() throws Exception {
        preparePromotionBasketItem("A", 6, 5);
        Integer result = basketItem.countPrice();

        assertEquals(result, new Integer(15));
    }

    @Test
    public void testCountPriceWithoutPromotion() throws Exception {
        prepareBasketItem("A", 6, 5);
        Integer result = basketItem.countPrice();

        assertEquals(result, new Integer(30));
    }

}