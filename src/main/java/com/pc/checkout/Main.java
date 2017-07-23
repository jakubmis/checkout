package com.pc.checkout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Mis on 2017-07-19.
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableAutoConfiguration
public class Main {
    public static void main( String[] args )
    {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

    }


}
