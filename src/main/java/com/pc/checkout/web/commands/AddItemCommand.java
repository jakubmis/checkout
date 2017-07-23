package com.pc.checkout.web.commands;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Created by jakub.mis on 7/20/2017.
 */
@Getter
@Setter
public class AddItemCommand {

    @NotNull
    private String name;
    @NotNull
    private Integer amount;
}
