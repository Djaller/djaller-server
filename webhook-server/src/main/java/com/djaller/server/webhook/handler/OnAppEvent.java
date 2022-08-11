package com.djaller.server.webhook.handler;

import com.djaller.common.event.model.AppEvent;
import com.djaller.common.event.model.EventHandlerConfig;
import com.djaller.server.webhook.service.CallWebhookService;
import com.djaller.server.webhook.service.EventsWebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Component(EventHandlerConfig.eventHandlerName)
public class OnAppEvent implements Consumer<AppEvent> {

    private final EventsWebhookService service;
    private final CallWebhookService callWebhookService;

    @Override
    public void accept(AppEvent appEvent) {
        log.debug("On new event {}", appEvent.getEventType());
        var webhooks = service.eventWebhooks(appEvent);
        webhooks.forEach(webhook -> callWebhookService.callWebhook(webhook, appEvent.getPayload()));
    }
}
