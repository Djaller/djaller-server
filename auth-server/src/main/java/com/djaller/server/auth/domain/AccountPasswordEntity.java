package com.djaller.server.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "account_password",
        indexes = @Index(name = "idx_account_id_account_password", columnList = "account_id")
)
public class AccountPasswordEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(columnDefinition = "text")
    private String value;

    @Column(nullable = false)
    private Boolean active;

    @Column(updatable = false, name = "account_id", columnDefinition = "uuid")
    private UUID accountId;
}
