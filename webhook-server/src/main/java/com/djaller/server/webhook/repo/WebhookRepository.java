package com.djaller.server.webhook.repo;

import com.djaller.server.webhook.domain.WebhookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WebhookRepository extends JpaRepository<WebhookEntity, UUID> {

    List<WebhookEntity> findByEventType(String type);

    List<WebhookEntity> findByTontineIdAndEventType(UUID tontineId, String type);

    List<WebhookEntity> findByTontineId(UUID tontineId);

}
