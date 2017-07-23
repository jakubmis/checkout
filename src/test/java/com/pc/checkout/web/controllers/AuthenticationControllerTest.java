package com.pc.checkout.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pc.checkout.Main;
import com.pc.checkout.web.commands.LoginCommand;
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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Mis on 2017-07-23.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Main.class)
public class AuthenticationControllerTest {

    @Autowired
    private AuthenticationController authenticationController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(authenticationController)
                .build();
    }

    @Test
    public void testLogin() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(post("/login")
                        .param("name", "ADMIN"))
                .andExpect(status().isOk())
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();

        final ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);

        assertNotNull(node.get("token").asText());
        assertTrue(node.get("status").asText().equals("OK"));
    }

    @Test
    public void testLoginUserNotFound() throws Exception {
        LoginCommand loginCommand = new LoginCommand();
        loginCommand.setName("ADMIN");
        MvcResult mvcResult = mockMvc
                .perform(post("/login")
                        .param("name", "NO_ACTIVE"))
                .andExpect(status().isOk())
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();

        final ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);

        assertNull(node.get("token"));
        assertTrue(node.get("status").asText().equals("No active customer"));
    }


}