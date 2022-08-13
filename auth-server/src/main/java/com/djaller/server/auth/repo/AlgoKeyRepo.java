package com.djaller.server.auth.repo;

import com.djaller.server.auth.core.BaseRepo;
import com.djaller.server.auth.domain.AlgoKeyEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AlgoKeyRepo extends BaseRepo<AlgoKeyEntity, UUID> {
}
