package com.pc.checkout;

import com.pc.checkout.domain.AuthenticationDomain;
import com.pc.checkout.persistence.entities.Customer;
import com.pc.checkout.persistence.entities.Item;
import com.pc.checkout.persistence.entities.ItemPromotion;
import com.pc.checkout.persistence.entities.Rebate;
import com.pc.checkout.persistence.repositories.CustomerRepository;
import com.pc.checkout.persistence.repositories.ItemRepository;
import com.pc.checkout.persistence.repositories.RebateRepository;
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
    private ConfigurableApplicationContext context;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthenticationDomain authenticationDomain;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private RebateRepository rebateRepository;

    @PostConstruct
    public void init() {
        createUsers();
        createItemsAnPromotions();
    }

    private void createUsers() {
        customerRepository.save(new Customer("ADMIN"));
        customerRepository.save(new Customer("MACIEJ"));
        customerRepository.save(new Customer("TOMEK"));
    }

    private void createItemsAnPromotions() {
        Item a = new Item(5, "APPLE");
        a.setPromotion(new ItemPromotion(5, 10));
        Item b = new Item(2, "BANANA");
        Item c = new Item(100, "CHERRY");
        c.setPromotion(new ItemPromotion(2, 80));
        Item d = new Item(10, "DILL");

        itemRepository.save(a);
        itemRepository.save(b);
        itemRepository.save(c);
        itemRepository.save(d);

        rebateRepository.save(new Rebate(50, asSet(a, b, c, d)));
        rebateRepository.save(new Rebate(10, asSet(a, b, c)));
    }
}
