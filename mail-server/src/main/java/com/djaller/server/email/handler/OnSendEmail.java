package com.djaller.server.email.handler;

import com.djaller.common.mail.model.EmailHandlerConfig;
import com.djaller.common.mail.model.SendMail;
import com.djaller.server.email.service.SaveEmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.function.Consumer;

@Slf4j
@AllArgsConstructor
@Component(EmailHandlerConfig.handlerName)
public class OnSendEmail implements Consumer<SendMail> {
    private final SaveEmailService service;

    @Override
    public void accept(@Valid SendMail sendEmail) {
        log.debug("Hande [send mail] to {}: {}", sendEmail.getTemplate(), sendEmail.getTo());
        service.saveEmail(sendEmail);
    }
}
