package com.pc.checkout;

import com.pc.checkout.domain.AuthenticationDomain;
import com.pc.checkout.domain.CheckoutDomain;
import com.pc.checkout.persistence.entities.Customer;
import com.pc.checkout.persistence.entities.Item;
import com.pc.checkout.persistence.entities.ItemPromotion;
import com.pc.checkout.persistence.entities.Rebate;
import com.pc.checkout.persistence.repositories.CustomerRepository;
import com.pc.checkout.persistence.repositories.ItemRepository;
import com.pc.checkout.persistence.repositories.RebateRepository;
import com.pc.checkout.utils.Response;
import com.pc.checkout.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static org.hibernate.validator.internal.util.CollectionHelper.asSet;

/**
 * Created by Mis on 2017-07-23.
 */
@Component
public class DataLoader {

    @Autowired
    ConfigurableApplicationContext context;

    @PostConstruct
    public void init() {
        CustomerRepository customerRepository = context.getBean(CustomerRepository.class);
        AuthenticationDomain authenticationDomain = context.getBean(AuthenticationDomain.class);
        customerRepository.save(new Customer("ADMIN"));

        Response<Token> login = authenticationDomain.login("ADMIN");

        Item a = new Item(6, "A");

        a.setPromotion(new ItemPromotion(5, 10));

        Item b = new Item(2, "B");
        Item c = new Item(100, "C");
        Item d = new Item(10, "D");

        ItemRepository itemRepository = context.getBean(ItemRepository.class);
        itemRepository.save(a);
        itemRepository.save(b);
        itemRepository.save(c);
        itemRepository.save(d);

        RebateRepository rebateRepository = context.getBean(RebateRepository.class);
        rebateRepository.save(new Rebate(10, asSet(a, b, c, d)));
        rebateRepository.save(new Rebate(100, asSet(a, b, c)));

        CheckoutDomain checkoutDomain = context.getBean(CheckoutDomain.class);
        checkoutDomain.putItemToBasket(login.getData(), "A", 10);
        checkoutDomain.putItemToBasket(login.getData(), "B", 10);
        checkoutDomain.putItemToBasket(login.getData(), "C", 10);

    }
}
