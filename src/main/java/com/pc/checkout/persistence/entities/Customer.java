package com.pc.checkout.persistence.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

/**
 * Created by jakub.mis on 7/20/2017.
 */
@Data
@Document(collection = "Customer")
public class Customer {

    @Id
    private String id;

    private String name;

    private String password;

    private Basket basket;



}
