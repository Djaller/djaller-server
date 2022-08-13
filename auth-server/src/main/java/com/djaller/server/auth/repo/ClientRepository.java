package com.djaller.server.auth.repo;

import com.djaller.server.auth.core.BaseRepo;
import com.djaller.server.auth.domain.ClientEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends BaseRepo<ClientEntity, String> {
    Optional<ClientEntity> findByClientId(String clientId);
}
