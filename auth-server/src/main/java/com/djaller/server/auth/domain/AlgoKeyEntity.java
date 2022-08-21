package com.djaller.server.auth.domain;

import com.djaller.server.auth.converter.PrivateKeyConverter;
import com.djaller.server.auth.converter.PublicKeyConverter;
import com.djaller.server.common.tenant.jpa.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "algorithm_jwks")
public class AlgoKeyEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Convert(converter = PublicKeyConverter.class)
    @Column(columnDefinition = "text")
    private PublicKey publicKey;

    @Convert(converter = PrivateKeyConverter.class)
    @Column(columnDefinition = "text")
    private PrivateKey privateKey;

    private String algorithm;
}
