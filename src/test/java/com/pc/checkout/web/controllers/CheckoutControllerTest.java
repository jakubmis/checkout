package com.pc.checkout.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pc.checkout.Main;
import com.pc.checkout.domain.CheckoutDomain;
import com.pc.checkout.utils.Receipt;
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

import java.io.IOException;

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
        validateReceipt(mvcResult);
    }

    @Test
    public void testPay() throws Exception {
        generateBasket();
        MvcResult payResult = mockMvc
                .perform(post("/checkout/item/pay")
                        .header("Authorization", token))
                .andReturn();
        validateReceipt(payResult);

        MvcResult scanResult = mockMvc
                .perform(get("/checkout/item/scan")
                        .header("Authorization", token))
                .andReturn();
        validateReceiptIsEmpty(scanResult);
    }

    private void validateReceipt(MvcResult mvcResult) throws IOException {
        String json = mvcResult.getResponse().getContentAsString();
        final Receipt receipt = mapper.readValue(json, Receipt.class);

        assertNotNull(receipt);
        receipt.getContent().forEach(product -> {
            assertNotNull(product.getPriceWithoutPromotion());
            assertNotNull(product.getFinalPrice());
            assertNotNull(product.getAmount());
            assertNotNull(product.getPromotionRefund());
            assertNotNull(product.getNumberOfGrantedPromotions());
            assertNotNull(product.getName());
        });
    }

    private void validateReceiptIsEmpty(MvcResult mvcResult) throws IOException {
        String json = mvcResult.getResponse().getContentAsString();
        final Receipt receipt = mapper.readValue(json, Receipt.class);
        assertNotNull(receipt);
        assertTrue(receipt.getContent().isEmpty());
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