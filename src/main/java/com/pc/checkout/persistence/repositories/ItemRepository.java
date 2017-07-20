package com.pc.checkout.persistence.repositories;

import com.pc.checkout.persistence.entities.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by jakub.mis on 7/20/2017.
 */
public interface ItemRepository extends MongoRepository<Item, String> {
}
