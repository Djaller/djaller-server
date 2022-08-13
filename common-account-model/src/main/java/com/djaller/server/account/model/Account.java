package com.djaller.server.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;

    private String email;
    private Boolean emailVerified;

    private String firstName;

    private String lastName;

    private String phoneNumber;
    private Boolean phoneNumberVerified;

    private AccountStatus status;
}
