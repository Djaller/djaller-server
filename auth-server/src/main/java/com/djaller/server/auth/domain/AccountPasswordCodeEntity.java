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
        name = "account_password_code",
        indexes = @Index(name = "idx_account_id_account_password_code", columnList = "account_id")
)
public class AccountPasswordCodeEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(columnDefinition = "text")
    private String codeHashed;

    @Column(nullable = false)
    private Boolean used;

    @Column(updatable = false, name = "account_id", columnDefinition = "uuid")
    private UUID accountId;
}
