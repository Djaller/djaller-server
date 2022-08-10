package com.djaller.server.email.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Data
@Embeddable
public class SendToEmbeddable {
    @NotEmpty
    private String email;

    private String name;
}
