package com.djaller.server.handler;

import com.djaller.common.event.model.AppEvent;
import com.djaller.common.event.model.EventHandlerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component(EventHandlerConfig.eventHandlerName)
public class OnAppEvent implements Consumer<AppEvent> {

    @Override
    public void accept(AppEvent appEvent) {
        log.info("TODO handle me {}", appEvent);
    }
}
