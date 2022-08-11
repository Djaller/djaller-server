package com.djaller.server.webhook.service;

import com.djaller.server.webhook.model.Webhook;

@FunctionalInterface
public interface CallWebhookService {

    void callWebhook(Webhook webhook, Object payload);

}
