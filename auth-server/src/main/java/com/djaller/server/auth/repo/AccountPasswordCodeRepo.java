package com.djaller.server.auth.repo;

import com.djaller.server.auth.core.BaseRepo;
import com.djaller.server.auth.domain.AccountPasswordCodeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountPasswordCodeRepo extends BaseRepo<AccountPasswordCodeEntity, UUID> {

    @Modifying
    @Query("UPDATE AccountPasswordCodeEntity a SET a.used = true WHERE a.accountId = :accountId")
    void deactivateAccountPassword(@Param("accountId") UUID accountId);

}
