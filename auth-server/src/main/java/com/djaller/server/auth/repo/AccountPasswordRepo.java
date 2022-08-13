package com.djaller.server.auth.repo;

import com.djaller.server.auth.core.BaseRepo;
import com.djaller.server.auth.domain.AccountPasswordEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountPasswordRepo extends BaseRepo<AccountPasswordEntity, UUID> {

    List<AccountPasswordEntity> findByAccountId(UUID accountId);

    AccountPasswordEntity findFirstByAccountIdAndActive(UUID accountId, boolean active);

    @Modifying
    @Query("""
UPDATE AccountPasswordEntity a
SET a.active = false
WHERE a.accountId = :accountId
""")
    void deactivateAccountPassword(@Param("accountId") UUID accountId);

}
