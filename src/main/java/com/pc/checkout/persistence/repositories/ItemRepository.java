package com.pc.checkout.persistence.repositories;

import com.pc.checkout.persistence.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jakub.mis on 7/20/2017.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findByName(String name);
}
