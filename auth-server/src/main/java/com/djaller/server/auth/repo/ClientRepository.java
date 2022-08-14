package com.djaller.server.auth.repo;

import com.djaller.server.auth.core.BaseRepo;
import com.djaller.server.auth.domain.AppClientEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends BaseRepo<AppClientEntity, String> {
    Optional<AppClientEntity> findByClientId(String clientId);
}
