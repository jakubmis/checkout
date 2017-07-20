package com.pc.checkout.persistence.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Map;

/**
 * Created by jakub.mis on 7/20/2017.
 */
@Data
@Document(collection = "Basket")
public class Basket {

    @Id
    private String id;

    private Map<Item, Integer> content;


}
