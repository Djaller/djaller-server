package com.djaller.server.repo;

import com.djaller.server.core.BaseRepo;
import com.djaller.server.domain.AuthorizationConsent;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorizationConsentRepository extends BaseRepo<AuthorizationConsent, AuthorizationConsent.AuthorizationConsentId> {
    Optional<AuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);

    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}
