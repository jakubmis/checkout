package com.pc.checkout.domain;

import com.pc.checkout.utils.Receipt;
import com.pc.checkout.utils.Response;
import com.pc.checkout.utils.Token;

/**
 * Created by jakub.mis on 7/20/2017.
 */
public interface ICheckoutDomain {

    Response putItemToBasket(Token token, String name, int amount);

    Receipt scanBasket(Token token);

    void clearBasket(Token token);
}
