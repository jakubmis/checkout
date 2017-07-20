package com.pc.checkout.persistence.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

/**
 * Created by jakub.mis on 7/20/2017.
 */
@Data
@Document(collection = "Item")
public class Item {

    @Id
    public String id;

    public Price price;

    public ItemName itemName;
}
