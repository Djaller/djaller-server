package com.djaller.server.webhook.service.impl;

import com.djaller.common.event.model.AppEvent;
import com.djaller.server.webhook.mapper.WebhookMapper;
import com.djaller.server.webhook.model.Webhook;
import com.djaller.server.webhook.repo.WebhookRepository;
import com.djaller.server.webhook.service.CallWebhookService;
import com.djaller.server.webhook.service.EventsWebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebhookServiceImpl implements CallWebhookService, EventsWebhookService {
    private final WebhookRepository repository;
    private final WebhookMapper webhookMapper;

    @Override
    public void callWebhook(Webhook webhook, Object payload) {
        log.debug("Calling a {} webhook with payload", webhook.getEventType());
        try {
            var restTemplate = new RestTemplate();
            var request = new HttpEntity<>(payload, new HttpHeaders());
            var response = restTemplate.postForEntity(webhook.getUrl(), request, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("Webhook call failed: {}", response.getBody());
            }
        } catch (Exception e) {
            log.error("Webhook call error", e);
        }
    }

    @Override
    public Collection<Webhook> eventWebhooks(AppEvent appEvent) {
        return repository
                .findByTontineIdAndEventType(appEvent.getTontineId(), appEvent.getEventType())
                .stream()
                .map(webhookMapper::toModel)
                .collect(Collectors.toSet());
    }
}
