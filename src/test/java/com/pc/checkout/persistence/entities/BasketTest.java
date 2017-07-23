package com.pc.checkout.persistence.entities;

import com.pc.checkout.utils.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;


/**
 * Created by Mis on 2017-07-23.
 */
public class BasketTest {

    private Basket basket;

    @Before
    public void setUp() throws Exception {
        basket = new Basket();
    }

    @Test
    public void testPut() throws Exception {
        Item item = new Item();
        basket.put(item, 1);

        assertEquals(basket.getContent().size(), 1);
    }

    @Test
    public void testPutIncrement() throws Exception {
        Item item = new Item();
        basket.put(item, 1);
        basket.put(item, 2);
        Integer amount = basket.getContent().iterator().next().getAmount();

        assertEquals(1, basket.getContent().size());
        assertEquals(amount, new Integer(3));
    }

    @Test
    public void testPutMultiple() throws Exception {
        Item item = new Item(1, "A");
        Item item2 = new Item(2, "B");
        basket.put(item, 1);
        basket.put(item2, 2);

        assertEquals(basket.getContent().size(), 2);
    }

    @Test
    public void testPrintContent() throws Exception {
        BasketItem basketItem = new BasketItem(prepareItem("A"), 2);
        BasketItem basketItem2 = new BasketItem(prepareItem("B"), 2);
        prepareBasket(basketItem, basketItem2);

        List<Product> actual = basket.getProducts();
        List<Product> expected = Stream.of(basketItem2.toProduct(), basketItem.toProduct())
                .collect(Collectors.toList());

        assertEquals(actual, expected);
    }

    private void prepareBasket(BasketItem... basketItems) {
        Set<BasketItem> collect = Arrays.stream(basketItems).collect(Collectors.toSet());
        basket.setContent(new HashSet<>(collect));
    }

    private Item prepareItem(String name) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(5);
        item.setPromotion(new ItemPromotion(5, 10));
        return item;
    }

    @Test
    public void testScanPrice() throws Exception {
        BasketItem basketItem = new BasketItem(prepareItem("A"), 2);
        BasketItem basketItem2 = new BasketItem(prepareItem("B"), 2);
        prepareBasket(basketItem, basketItem2);

        Integer actual = basket.scanPrice();
        Integer expected = basketItem.countPrice() + basketItem2.countPrice();

        assertEquals(actual, expected);
    }

}