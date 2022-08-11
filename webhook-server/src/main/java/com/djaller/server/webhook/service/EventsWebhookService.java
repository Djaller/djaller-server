package com.djaller.server.webhook.service;

import com.djaller.common.event.model.AppEvent;
import com.djaller.server.webhook.model.Webhook;

import java.util.Collection;

@FunctionalInterface
public interface EventsWebhookService {

    Collection<Webhook> eventWebhooks(AppEvent appEvent);

}
