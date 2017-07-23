package com.pc.checkout.web.commands;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Created by Mis on 2017-07-22.
 */
@Getter
@Setter
public class LoginCommand {

    @NotNull
    private String name;
}
