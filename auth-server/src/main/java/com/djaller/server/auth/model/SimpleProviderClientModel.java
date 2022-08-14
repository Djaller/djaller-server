package com.djaller.server.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleProviderClientModel {
    private UUID id;
    private String registrationId;
    private String clientId;
    private String clientName;
}
