package com.djaller.server.auth.repo;

import com.djaller.server.auth.core.BaseRepo;
import com.djaller.server.auth.domain.ProviderClientEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRegistrationRepo extends BaseRepo<ProviderClientEntity, UUID> {

    Optional<ProviderClientEntity> findByRegistrationId(String registrationId);

    List<ProviderClientEntity> findBySystemClient(boolean systemClient);
}
