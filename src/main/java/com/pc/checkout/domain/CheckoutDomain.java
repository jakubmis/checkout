package com.pc.checkout.domain;

import com.pc.checkout.persistence.repositories.ItemRepository;
import org.springframework.stereotype.Service;

/**
 * Created by jakub.mis on 7/20/2017.
 */
@Service
public class CheckoutDomain {

    private ItemRepository itemRepository;

    public CheckoutDomain(ItemRepository mgItemRepository) {
        this.itemRepository = itemRepository;
    }

    public void addToBasket(){

    }
}
