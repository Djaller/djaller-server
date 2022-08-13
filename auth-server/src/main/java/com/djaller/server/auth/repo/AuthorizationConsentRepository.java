package com.djaller.server.auth.repo;

import com.djaller.server.auth.core.BaseRepo;
import com.djaller.server.auth.domain.AuthorizationConsentEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorizationConsentRepository extends BaseRepo<AuthorizationConsentEntity, AuthorizationConsentEntity.AuthorizationConsentId> {
    Optional<AuthorizationConsentEntity> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);

    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}
