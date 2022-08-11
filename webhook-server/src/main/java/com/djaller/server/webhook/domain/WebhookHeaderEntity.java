package com.djaller.server.webhook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "webhook_headers", indexes = @Index(name = "idx_webhook_id", columnList = "webhook_id"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebhookHeaderEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @NotEmpty
    @Column(name = "map_key")
    private String key;

    @NotEmpty
    @ElementCollection
    @CollectionTable(
            name = "webhook_multi_header_values",
            joinColumns = @JoinColumn(name = "multi_header_id")
    )
    private Set<String> valuesSet;

    @ManyToOne
    @JoinColumn(name = "webhook_id")
    private WebhookEntity webhook;
}
