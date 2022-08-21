package com.djaller.server.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;

    @URL
    private String pictureUrl;

    private String email;
    private Boolean emailVerified;

    private String firstName;
    private String lastName;

    private String birthDate;
    private String gender;
    private String locale;

    private String phoneNumber;
    private Boolean phoneNumberVerified;

    private AccountStatus status;
}
