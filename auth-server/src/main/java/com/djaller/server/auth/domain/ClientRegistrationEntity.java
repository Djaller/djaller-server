package com.djaller.server.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client_registrations")
public class ClientRegistrationEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String registrationId;

    @GeneratedValue
    @Column(nullable = false, unique = true)
    private String clientId;

    @Column(nullable = false)
    private String clientName;

    @JsonIgnore
    private String clientSecret;

    @Enumerated(EnumType.STRING)
    private ClientAuthenticationMethod clientAuthenticationMethod;

    @Enumerated(EnumType.STRING)
    private AuthorizationGrantType authorizationGrantType;

    private String redirectUri;

    @BatchSize(size = 5)
    @CollectionTable(name = "client_registration_scopes")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> scopes;

    // Detail
    private String detailAuthorizationUri;

    private String detailTokenUri;

    private String detailUserInfoUri;

    private String detailUserInfoAttributeName;

    @Enumerated(EnumType.STRING)
    private AuthenticationMethod detailUserInfoAuthenticationMethod;

    @Column(length = 1000)
    private String detailJwkSetUri;

    @Column(length = 1000)
    private String detailIssuerUri;

    @CollectionTable(name = "client_registration_configuration_metadata")
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, MapString> detailConfigurationMetadata;

    public enum AuthenticationMethod {
        HEADER, FORM, QUERY
    }

    public enum AuthorizationGrantType {
        AUTHORIZATION_CODE, REFRESH_TOKEN, CLIENT_CREDENTIALS, PASSWORD, JWT_BEARER,
    }

    public enum ClientAuthenticationMethod {
        CLIENT_SECRET_BASIC, CLIENT_SECRET_POST, CLIENT_SECRET_JWT, PRIVATE_KEY_JWT, NONE
    }

}
