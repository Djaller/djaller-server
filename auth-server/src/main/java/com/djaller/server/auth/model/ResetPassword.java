package com.djaller.server.auth.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class ResetPassword {
    @NotEmpty
    private String password;

    @NotEmpty
    private String confirm;

    @NotEmpty
    private String codeVerifier;

    @NotNull
    private UUID accountId;
}
