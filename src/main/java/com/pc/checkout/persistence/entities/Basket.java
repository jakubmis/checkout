package com.pc.checkout.persistence.entities;

import com.pc.checkout.utils.Product;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by jakub.mis on 7/20/2017.
 */
@Data
@Entity
@Table(name = "BASKET")
public class Basket {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "BASKET_ID", referencedColumnName = "ID")
    private Set<BasketItem> content;

    public Basket() {
        this.content = new HashSet<>();
    }

    private Optional<BasketItem> getItem(Item item) {
        return content.stream()
                .filter(basketItem -> basketItem.getItem().equals(item))
                .findFirst();
    }

    public void put(Item item, int unit) {
        Optional<BasketItem> optionalItem = getItem(item);
        if (optionalItem.isPresent()) {
            optionalItem.get().incrementAmountBy(unit);
        } else {
            content.add(new BasketItem(item, unit));
        }
    }

    public List<Product> getProducts() {
        return content.stream()
                .map(BasketItem::toProduct)
                .collect(Collectors.toList());
    }

    public Integer scanPrice() {
        return content
                .stream()
                .map(BasketItem::countPrice)
                .collect(Collectors.summingInt(Integer::intValue));
    }
}
