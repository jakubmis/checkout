package com.pc.checkout.persistence.repositories;

import com.pc.checkout.persistence.entities.Rebate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

/**
 * Created by Mis on 2017-07-22.
 */
public interface RebateRepository extends JpaRepository<Rebate, Long> {

    String GET_REBATE_QUERY = "SELECT TOP 1 r.REBATE FROM REBATE r WHERE r.ID IN" +
            "(SELECT r.REBATE_ID FROM REBATE_ITEMS r WHERE " +
            "r.REBATE_ID not IN (SELECT r.REBATE_ID FROM REBATE_ITEMS r WHERE R.ITEM_ID not in :itemIds) GROUP BY r.REBATE_ID)" +
            "order by r.REBATE desc";

    @Query(value = GET_REBATE_QUERY, nativeQuery = true)
    Optional<Integer> calculateHighestRebate(@Param("itemIds") Set<Long> itemIds);
}
