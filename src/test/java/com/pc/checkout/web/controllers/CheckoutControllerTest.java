package com.pc.checkout.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pc.checkout.Main;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by Mis on 2017-07-23.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Main.class)
public class CheckoutControllerTest {

    @Autowired
    private CheckoutController checkoutController;

    @Autowired
    private AuthenticationController authenticationController;

    private MockMvc mockMvc;
    private String token;
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(checkoutController)
                .build();
        generateToken();

    }

    @Test
    public void testAddItemToBasket() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(post("/checkout/item/add")
                        .param("name", "A")
                        .param("amount", "5")
                        .header("Authorization", token))
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        final ObjectNode node = mapper.readValue(json, ObjectNode.class);

        assertTrue(node.get("status").asText().equals("OK"));
    }

    @Test
    public void testScan() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(post("/checkout/item/scan")
                        .header("Authorization", token))
                .andReturn();
        String receipt = mvcResult.getResponse().getContentAsString();

        assertNotNull(receipt);
    }

    @Test
    public void testPay() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(post("/checkout/item/pay")
                        .header("Authorization", token))
                .andReturn();
        String receipt = mvcResult.getResponse().getContentAsString();

        assertNotNull(receipt);
    }

    public void generateToken() throws Exception {
        MockMvc tokenMVC = MockMvcBuilders
                .standaloneSetup(authenticationController)
                .build();
        MvcResult mvcResult = tokenMVC
                .perform(post("/login")
                        .param("name", "ADMIN"))
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        final ObjectNode node = mapper.readValue(json, ObjectNode.class);
        token = node.get("token").asText();
    }
}