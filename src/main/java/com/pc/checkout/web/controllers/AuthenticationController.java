package com.pc.checkout.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pc.checkout.domain.IAuthenticationDomain;
import com.pc.checkout.utils.Response;
import com.pc.checkout.utils.Token;
import com.pc.checkout.web.commands.LoginCommand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Mis on 2017-07-22.
 */
@RestController
@RequestMapping(value = "/")
public class AuthenticationController {

    private IAuthenticationDomain iAuthenticationDomain;
    private ObjectMapper mapper;

    public AuthenticationController(IAuthenticationDomain iAuthenticationDomain, ObjectMapper mapper) {
        this.iAuthenticationDomain = iAuthenticationDomain;
        this.mapper = mapper;
    }

    @RequestMapping(method = RequestMethod.POST, value = "login")
    public ObjectNode login(@Valid LoginCommand loginCommand) {
        ObjectNode jsonObject = mapper.createObjectNode();
        Response<Token> response = iAuthenticationDomain.login(loginCommand.getName());
        jsonObject.put("status", response.getMessage());
        if (response.getData() != null) {
            jsonObject.put("token", response.getData().getValue());
        }
        return jsonObject;
    }

}
