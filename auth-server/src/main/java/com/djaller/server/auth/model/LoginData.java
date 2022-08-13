package com.djaller.server.auth.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginData {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private boolean rememberMe;
}
