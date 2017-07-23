package com.pc.checkout.domain;

import com.pc.checkout.persistence.entities.Customer;
import com.pc.checkout.utils.Response;
import com.pc.checkout.utils.Token;

/**
 * Created by Mis on 2017-07-22.
 */
public interface IAuthenticationDomain {

    Response<Token> login(String name);

    Customer getCustomer(Token token);

    boolean containsToken(Token token);
}
