package com.pc.checkout.persistence.repositories.sql;

import com.pc.checkout.persistence.entities.sql.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jakub.mis on 7/20/2017.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
