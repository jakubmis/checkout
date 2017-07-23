package com.pc.checkout.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pc.checkout.Main;
import com.pc.checkout.domain.CheckoutDomain;
import com.pc.checkout.utils.Token;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by Mis on 2017-07-23.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Main.class)
public class CheckoutControllerTest {

    final private static String RECEIPT_HEADER = "Receipt\n------------\n";
    final private static String RECEIPT_FOOTER = "------------\n" + "Price: 440\n" + "Rebate granted: 50\n" + "Total price: 390";
    final private static String RECEIPT_CONTENT_APPLE = "|Product: APPLE|Amount: 10|Price: 50|AmountOfPromotion: 2|Promotion: 30|Total: 20\n";
    final private static String RECEIPT_CONTENT_BANANA = "|Product: BANANA|Amount: 10|Price: 20|AmountOfPromotion: 0|Promotion: 0|Total: 20\n";
    final private static String RECEIPT_CONTENT_CHERRY = "|Product: CHERRY|Amount: 10|Price: 1000|AmountOfPromotion: 5|Promotion: 600|Total: 400";


    @Autowired
    private ConfigurableApplicationContext context;
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
                        .param("name", "APPLE")
                        .param("amount", "5")
                        .header("Authorization", token))
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        final ObjectNode node = mapper.readValue(json, ObjectNode.class);

        assertTrue(node.get("status").asText().equals("OK"));
    }

    @Test
    public void testScan() throws Exception {
        generateBasket();
        MvcResult mvcResult = mockMvc
                .perform(get("/checkout/item/scan")
                        .header("Authorization", token))
                .andReturn();
        String receipt = mvcResult.getResponse().getContentAsString();

        assertNotNull(receipt);
        assertTrue(receipt.contains(RECEIPT_HEADER));
        assertTrue(receipt.contains(RECEIPT_FOOTER));
        assertTrue(receipt.contains(RECEIPT_CONTENT_APPLE));
        assertTrue(receipt.contains(RECEIPT_CONTENT_BANANA));
        assertTrue(receipt.contains(RECEIPT_CONTENT_CHERRY));
    }

    @Test
    public void testPay() throws Exception {
        generateBasket();
        MvcResult mvcResult = mockMvc
                .perform(post("/checkout/item/pay")
                        .header("Authorization", token))
                .andReturn();
        String receipt = mvcResult.getResponse().getContentAsString();


        assertNotNull(receipt);
        assertTrue(receipt.contains(RECEIPT_HEADER));
        assertTrue(receipt.contains(RECEIPT_FOOTER));
        assertTrue(receipt.contains(RECEIPT_CONTENT_APPLE));
        assertTrue(receipt.contains(RECEIPT_CONTENT_BANANA));
        assertTrue(receipt.contains(RECEIPT_CONTENT_CHERRY));
    }

    private void generateToken() throws Exception {
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

    private void generateBasket() throws Exception {
        CheckoutDomain checkoutDomain = context.getBean(CheckoutDomain.class);
        checkoutDomain.putItemToBasket(new Token(token), "APPLE", 10);
        checkoutDomain.putItemToBasket(new Token(token), "BANANA", 10);
        checkoutDomain.putItemToBasket(new Token(token), "CHERRY", 10);
    }
}