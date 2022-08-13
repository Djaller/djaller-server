package com.djaller.server.mainapi.repo;

import com.djaller.server.mainapi.domain.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.validation.constraints.Email;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepo extends JpaRepository<AccountEntity, UUID>, JpaSpecificationExecutor<AccountEntity> {
    Optional<AccountEntity> findByEmail(@Email String email);
}
