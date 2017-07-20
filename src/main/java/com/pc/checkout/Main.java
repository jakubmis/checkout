package com.pc.checkout;

import com.pc.checkout.persistence.entities.ItemName;
import com.pc.checkout.persistence.entities.Item;
import com.pc.checkout.persistence.entities.Price;
import com.pc.checkout.persistence.repositories.ItemRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by Mis on 2017-07-19.
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableMongoRepositories
@EnableAutoConfiguration
public class Main {
    public static void main( String[] args )
    {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        ItemRepository bean = context.getBean(ItemRepository.class);
        Item item = new Item();
        item.setPrice(new Price(5, "USD"));
        item.setItemName(new ItemName("Jabłko"));
        bean.save(item);

        System.out.println(bean.findAll());
//        ItemRepository bean = context.getBean(ItemRepository.class);
//        Item item = new Item();
//        item.setName("Jabłko");
//        item.setPrice(1);
//        bean.save(item);
//
//        System.out.println(bean.findOne(1L));
    }
}
