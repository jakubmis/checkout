package com.pc.checkout.utils;

import com.pc.checkout.persistence.entities.Customer;
import lombok.Data;
import org.springframework.util.Assert;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by Mis on 2017-07-22.
 */
@Data
public class Token {

    static Clock clock = Clock.systemDefaultZone();
    private String value;

    public Token(String value) {
        this.value = value;
    }

    public static Optional<Token> generateToken(Customer customer) {
        Assert.notNull(customer, "Can't generate token without data");
        Token token = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update((customer.getName() + LocalDateTime.now(clock)).getBytes());
            String encryptedString = DatatypeConverter.printHexBinary(messageDigest.digest()).toUpperCase();
            token = new Token(encryptedString);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(token);
    }


}
