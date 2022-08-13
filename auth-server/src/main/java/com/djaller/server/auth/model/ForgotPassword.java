package com.djaller.server.auth.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class ForgotPassword {

    @Email
    @NotNull
    private String username;

}
