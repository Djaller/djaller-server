package com.djaller.server.repo;

import com.djaller.server.core.BaseRepo;
import com.djaller.server.domain.Authorization;
import com.djaller.server.domain.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends BaseRepo<Client, String> {
    Optional<Client> findByClientId(String clientId);
}
