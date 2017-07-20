package com.pc.checkout.persistence.entities.sql;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by jakub.mis on 7/20/2017.
 */
@Entity
@Data
@Table(name = "ITEM")
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "PRICE", nullable = false)
    private Integer price;

}
