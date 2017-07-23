package com.pc.checkout.persistence.repositories;

import com.pc.checkout.persistence.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by Mis on 2017-07-22.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByName(String admin);
}
