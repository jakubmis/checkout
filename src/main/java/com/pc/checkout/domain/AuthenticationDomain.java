package com.pc.checkout.domain;

import com.pc.checkout.persistence.entities.Customer;
import com.pc.checkout.persistence.repositories.CustomerRepository;
import com.pc.checkout.utils.Response;
import com.pc.checkout.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created by Mis on 2017-07-22.
 */
@Service
public class AuthenticationDomain implements IAuthenticationDomain {

    private CustomerRepository customerRepository;
    private HashMap<Token, String> tokens;

    @Autowired
    public AuthenticationDomain(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.tokens = new HashMap<>();
    }

    @Override
    @Transactional
    public Response<Token> login(String name) {
        Customer customer = customerRepository.findByName(name);
        return Optional.ofNullable(customer)
                .map(this::generateToken)
                .orElse(Response.create("No active customer"));
    }

    private Response<Token> generateToken(Customer customer) {
        return Token.generateToken(customer)
                .map(token -> populateTokenStorage(token, customer.getName()))
                .orElse(Response.create("Error while generating token"));
    }

    private Response<Token> populateTokenStorage(Token token, String name) {
        tokens.put(token, name);
        return Response.create(token, Response.OK);
    }

    @Override
    @Transactional
    public Customer getCustomer(Token token) {
        String customerName = tokens.get(token);
        return customerRepository.findByName(customerName);
    }

    @Override
    public boolean containsToken(Token token) {
        if (token == null || token.getValue() == null) {
            return false;
        }
        return tokens.containsKey(token);
    }


}
