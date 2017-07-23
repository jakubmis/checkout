package com.pc.checkout.utils;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Mis on 2017-07-23.
 */
public class ResponseTest {

    private static final String EMPTY = "";
    private static final String TEST_MESSAGE = "TEST_MESSAGE";

    @Test
    public void testCreate() throws Exception {
        Response response = Response.create(TEST_MESSAGE);
        assertEquals(TEST_MESSAGE, response.getMessage());
    }

    @Test
    public void testCreateNullMessage() throws Exception {
        String message = null;
        Response response = Response.create(message);
        assertEquals(EMPTY, response.getMessage());
    }

    @Test
    public void testCreate1() throws Exception {
        String message = TEST_MESSAGE;
        Integer data = 5;
        Response<Integer> response = Response.create(5, message);
        assertEquals(message, response.getMessage());
        assertEquals(data, response.getData());
    }

    @Test
    public void testCreate1NullMessage() throws Exception {
        String message = null;
        Integer data = 5;
        Response<Integer> response = Response.create(5, message);
        assertEquals(EMPTY, response.getMessage());
        assertEquals(data, response.getData());
    }

    @Test
    public void testCreate1NullData() throws Exception {
        String message = TEST_MESSAGE;
        Integer data = null;
        Response<Integer> response = Response.create(data, message);
        assertEquals(TEST_MESSAGE, response.getMessage());
        assertNull(response.getData());
    }


    @Test
    public void testSuccess() throws Exception {
        Response success = Response.success();
        assertEquals(Response.OK, success.getMessage());
        assertNull(success.getData());
    }

}