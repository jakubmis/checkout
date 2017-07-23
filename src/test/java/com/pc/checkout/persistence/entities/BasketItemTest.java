package com.pc.checkout.persistence.entities;


import com.pc.checkout.utils.Product;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Mis on 2017-07-23.
 */
public class BasketItemTest {

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
    public void testToProduct() throws Exception {
        preparePromotionBasketItem("A", 6, 5);

        Product product = basketItem.toProduct();

        assertNotNull(product);
        assertEquals(product.getName(), basketItem.getName());
        assertEquals(product.getAmount(), basketItem.getAmount());
        assertEquals(product.getPriceWithoutPromotion(), new Integer(30));
        assertEquals(product.getNumberOfGrantedPromotions(), new Integer(1));
        assertEquals(product.getPromotionRefund(), new Integer(15));
        assertEquals(product.getFinalPrice(), new Integer(15));

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

        Product product = basketItem.toProduct();

        assertNotNull(product);
        assertEquals(product.getName(), basketItem.getName());
        assertEquals(product.getAmount(), basketItem.getAmount());
        assertEquals(product.getPriceWithoutPromotion(), new Integer(30));
        assertEquals(product.getNumberOfGrantedPromotions(), new Integer(0));
        assertEquals(product.getPromotionRefund(), new Integer(0));
        assertEquals(product.getFinalPrice(), new Integer(30));

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