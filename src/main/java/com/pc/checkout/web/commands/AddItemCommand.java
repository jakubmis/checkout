package com.pc.checkout.web.commands;

import lombok.Data;

/**
 * Created by jakub.mis on 7/20/2017.
 */
@Data
public class AddItemCommand {

    private String name;
    private Integer amount;
}
