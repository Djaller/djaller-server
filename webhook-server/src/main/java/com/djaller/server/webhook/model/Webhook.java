package com.djaller.server.webhook.model;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Data
public class Webhook implements Serializable {
    private UUID id;

    @URL(protocol = "https")
    private String url;

    @NotNull
    private UUID tontineId;

    @NotEmpty
    private String eventType;

    private Map<String, Collection<String>> headers;

}
