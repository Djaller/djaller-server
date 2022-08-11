package com.djaller.server.webhook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "webhooks", indexes = {
        @Index(name = "idx_webhook_tontine_id", columnList = "tontine_id"),
        @Index(name = "idx_webhook_tontine_id_type", columnList = "tontine_id,event_type"),
        @Index(name = "idx_webhook_type", columnList = "event_type"),
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebhookEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @NotEmpty
    @Column(name = "url")
    private String url;

    @NotNull
    @Column(name = "tontine_id", updatable = false, columnDefinition = "uuid")
    private UUID tontineId;

    @Column(name = "event_type", updatable = false)
    private String eventType;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "webhook")
    private Set<WebhookHeaderEntity> headers;
}
