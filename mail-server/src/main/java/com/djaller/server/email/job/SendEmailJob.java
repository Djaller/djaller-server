package com.djaller.server.email.job;

import com.djaller.server.email.service.SendEmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class SendEmailJob {

    private final SendEmailService service;

    @Scheduled(cron = "*/10 * * * * *")
    @SchedulerLock(name = "shortRunningTask", lockAtMostFor = "50s", lockAtLeastFor = "30s")
    public void batchSendEmail() {
        log.debug("Running batch send email...");
        service.sendAllEmail();
    }

}
