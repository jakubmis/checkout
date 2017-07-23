package com.pc.checkout.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Mis on 2017-07-22.
 */
@Entity
@Data
@Table(name = "REBATE")
public class Rebate {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "REBATE", nullable = false)
    private Integer rebate;

    @ManyToMany
    @JoinTable(
            name = "REBATE_ITEMS",
            joinColumns = @JoinColumn(name = "REBATE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID", referencedColumnName = "ID"))
    private Set<Item> discountItems;

    public Rebate() {
    }

    public Rebate(Integer rebate, Set<Item> discountItems) {
        this.rebate = rebate;
        this.discountItems = discountItems;
    }
}
