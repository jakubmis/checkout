package com.pc.checkout.domain;

import com.pc.checkout.persistence.entities.Customer;
import com.pc.checkout.persistence.repositories.CustomerRepository;
import com.pc.checkout.utils.Response;
import com.pc.checkout.utils.Token;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by Mis on 2017-07-23.
 */
public class AuthenticationDomainTest {

    @InjectMocks
    private AuthenticationDomain authenticationDomain;

    @Mock
    private CustomerRepository customerRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoginAndContainsToken() throws Exception {
        when(customerRepository.findByName(eq("ADMIN"))).thenReturn(new Customer());
        Response<Token> response = authenticationDomain.login("ADMIN");

        assertNotNull(response.getData());
        assertNotNull(response.getData().getValue());
        assertEquals(response.getMessage(), Response.OK);

        boolean tokenStored = authenticationDomain.containsToken(response.getData());
        assertTrue(tokenStored);
    }

    @Test
    public void testGetCustomer() throws Exception {
        Customer expected = new Customer();
        expected.setName("ADMIN");
        when(customerRepository.findByName(eq("ADMIN"))).thenReturn(expected);
        Response<Token> response = authenticationDomain.login("ADMIN");

        Customer customer = authenticationDomain.getCustomer(response.getData());
        assertEquals(customer, expected);
    }

    @Test
    public void testContainsTokenFailure() throws Exception {
        boolean tokenStored = authenticationDomain.containsToken(new Token("A"));
        assertFalse(tokenStored);
    }

}