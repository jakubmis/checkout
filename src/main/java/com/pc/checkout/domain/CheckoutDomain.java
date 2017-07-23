package com.pc.checkout.domain;

import com.pc.checkout.persistence.entities.Basket;
import com.pc.checkout.persistence.entities.BasketItem;
import com.pc.checkout.persistence.entities.Customer;
import com.pc.checkout.persistence.entities.Item;
import com.pc.checkout.persistence.repositories.CustomerRepository;
import com.pc.checkout.persistence.repositories.ItemRepository;
import com.pc.checkout.persistence.repositories.RebateRepository;
import com.pc.checkout.utils.Receipt;
import com.pc.checkout.utils.Response;
import com.pc.checkout.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by jakub.mis on 7/20/2017.
 */
@Service
public class CheckoutDomain implements ICheckoutDomain {

    private ItemRepository itemRepository;
    private CustomerRepository customerRepository;
    private AuthenticationDomain authenticationDomain;
    private RebateRepository rebateRepository;

    @Autowired
    public CheckoutDomain(ItemRepository itemRepository, CustomerRepository customerRepository,
                          AuthenticationDomain authenticationDomain, RebateRepository rebateRepository) {
        this.itemRepository = itemRepository;
        this.customerRepository = customerRepository;
        this.authenticationDomain = authenticationDomain;
        this.rebateRepository = rebateRepository;
    }

    @Override
    @Transactional
    public Response putItemToBasket(Token token, String name, int amount) {
        Item item = itemRepository.findByName(name);
        if (item == null) {
            return Response.create("Requested item is not available.");
        }
        getBasket(token).put(item, amount);
        return Response.success();
    }

    @Override
    @Transactional
    public Receipt scanBasket(Token token) {
        Basket basket = getBasket(token);
        Integer grantedRebate = calculateRebate(basket);
        return new Receipt(basket.printContent(), basket.scanPrice(), grantedRebate);
    }

    @Override
    @Transactional
    public void clearBasket(Token token) {
        Customer customer = authenticationDomain.getCustomer(token);
        customer.clearBasket();
    }

    private Integer calculateRebate(Basket basket) {
        Set<Long> collect = basket.getContent()
                .stream()
                .map(BasketItem::getItem)
                .map(Item::getId)
                .collect(Collectors.toSet());
        return rebateRepository.calculateHighestRebate(collect, collect.size())
                .orElse(0);
    }

    private Basket getBasket(Token token) {
        return authenticationDomain.getCustomer(token).getBasket();
    }

}
