package com.pc.checkout.domain;

import com.pc.checkout.persistence.entities.BasketItem;
import com.pc.checkout.persistence.entities.Customer;
import com.pc.checkout.persistence.entities.Item;
import com.pc.checkout.persistence.repositories.CustomerRepository;
import com.pc.checkout.persistence.repositories.ItemRepository;
import com.pc.checkout.persistence.repositories.RebateRepository;
import com.pc.checkout.utils.Receipt;
import com.pc.checkout.utils.Token;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by Mis on 2017-07-23.
 */
public class CheckoutDomainTest {

    @InjectMocks
    private CheckoutDomain checkoutDomain;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AuthenticationDomain authenticationDomain;
    @Mock
    private RebateRepository rebateRepository;

    private Token token;
    private Customer customer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        token = new Token("ADMIN");
        customer = new Customer("ADMIN");
        when(authenticationDomain.getCustomer(any())).thenReturn(customer);
    }

    @Test
    public void testPutItemToBasket() throws Exception {
        Item item = new Item(6, "apple");
        when(itemRepository.findByName(eq("apple"))).thenReturn(item);

        checkoutDomain.putItemToBasket(token, "apple", 5);

        assertTrue(!customer.getBasket().getContent().isEmpty());
        assertEquals(customer.getBasket().getContent().iterator().next(), new BasketItem(item, 5));
    }

    @Test
    public void testScanBasket() throws Exception {
        Receipt expected = prepareScanBasketData();
        Receipt actual = checkoutDomain.scanBasket(token);
        assertEquals(actual, expected);
    }

    private Receipt prepareScanBasketData() {
        Item item = new Item(5, "apple");
        item.setId(1L);
        customer.getBasket().put(item, 5);
        Set<Long> itemIds = new HashSet<>();
        itemIds.add(1L);
        final Integer REBATE = 100;
        Receipt expected = new Receipt(customer.getBasket().printContent(), customer.getBasket().scanPrice(), REBATE);
        when(rebateRepository.calculateHighestRebate(eq(itemIds), eq(1))).thenReturn(Optional.of(REBATE));
        return expected;
    }

    @Test
    public void testClearBasket() throws Exception {
        customer.getBasket().put(new Item(), 5);
        checkoutDomain.clearBasket(token);
        assertTrue(customer.getBasket().getContent().isEmpty());
    }

}