package com.pc.checkout.utils;

import com.pc.checkout.persistence.entities.Customer;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by Mis on 2017-07-23.
 */
public class TokenTest {

    private static final String TOKEN_VALUE = "TOKEN_VALUE";
    private Customer customer;
    private Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

    @Before
    public void setUp() throws Exception {
        customer = new Customer(TOKEN_VALUE);
    }

    @Test
    public void testGenerateToken() throws Exception {
        Token.clock = clock;
        Optional<Token> token = Token.generateToken(customer);
        assertTrue(token.isPresent());
        assertEquals(MD5(TOKEN_VALUE), token.get().getValue());
    }

    private String MD5(String string) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update((string + LocalDateTime.now(clock)).getBytes());
        return DatatypeConverter.printHexBinary(messageDigest.digest()).toUpperCase();
    }

}