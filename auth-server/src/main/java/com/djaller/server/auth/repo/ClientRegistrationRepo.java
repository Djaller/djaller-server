package com.djaller.server.auth.repo;

import com.djaller.server.auth.core.BaseRepo;
import com.djaller.server.auth.domain.ClientRegistrationEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRegistrationRepo extends BaseRepo<ClientRegistrationEntity, UUID> {

    Optional<ClientRegistrationEntity> findByRegistrationId(String registrationId);
}
