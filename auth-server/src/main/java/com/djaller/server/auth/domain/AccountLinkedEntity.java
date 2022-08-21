package com.djaller.server.auth.domain;

import com.djaller.server.auth.model.oauth.OAuth2UserInfoType;
import com.djaller.server.common.tenant.jpa.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "account_linked",
        indexes = {
                @Index(name = "idx_account_id_account_linked", columnList = "account_id"),
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_account_id_by_type", columnNames = {"account_id", "type"})
        }
)
public class AccountLinkedEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(updatable = false, name = "account_id", columnDefinition = "uuid", nullable = false)
    private UUID accountId;

    @Enumerated(EnumType.STRING)
    private OAuth2UserInfoType type;

    @CollectionTable(name = "account_linked_metadata")
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, MapString> metaData;

}
