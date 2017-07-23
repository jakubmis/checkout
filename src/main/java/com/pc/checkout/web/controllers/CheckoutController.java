package com.pc.checkout.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pc.checkout.domain.ICheckoutDomain;
import com.pc.checkout.utils.Response;
import com.pc.checkout.utils.Token;
import com.pc.checkout.web.commands.AddItemCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

/**
 * Created by jakub.mis on 7/20/2017.
 */
@RestController
@RequestMapping(value = "/checkout/")
public class CheckoutController {

    private ICheckoutDomain iCheckoutDomain;

    private ObjectMapper mapper;

    @Autowired
    public CheckoutController(ICheckoutDomain iCheckoutDomain, ObjectMapper mapper) {
        this.iCheckoutDomain = iCheckoutDomain;
        this.mapper = mapper;
    }

    @RequestMapping(method = RequestMethod.POST, value = "item/add")
    public ObjectNode addItemToBasket(@Valid AddItemCommand addItemCommand, Errors errors,
                                      @RequestHeader("Authorization") final String auth) {
        ObjectNode jsonObject = mapper.createObjectNode();
        if (errors.hasErrors()) {
            return jsonObject.put("status", errors.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", ")));
        }
        Response response = iCheckoutDomain.putItemToBasket(new Token(auth), addItemCommand.getName(), addItemCommand.getAmount());
        jsonObject.put("status", response.getMessage());
        return jsonObject;
    }

    @RequestMapping(method = RequestMethod.GET, value = "item/scan")
    public String scan(@RequestHeader("Authorization") final String auth) {
        return iCheckoutDomain.scanBasket(new Token(auth)).print();
    }

    @RequestMapping(method = RequestMethod.POST, value = "item/pay")
    public String pay(@RequestHeader("Authorization") final String auth) {
        String receipt = iCheckoutDomain.scanBasket(new Token(auth)).print();
        iCheckoutDomain.clearBasket(new Token(auth));
        return receipt;
    }

}
