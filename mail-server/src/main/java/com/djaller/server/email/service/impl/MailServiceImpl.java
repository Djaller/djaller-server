package com.djaller.server.email.service.impl;

import com.djaller.common.mail.model.EmailTemplate;
import com.djaller.common.mail.model.SendMail;
import com.djaller.server.email.domain.MailTemplateEntity;
import com.djaller.server.email.domain.SendMailEntity;
import com.djaller.server.email.domain.SendMailStatus;
import com.djaller.server.email.domain.SendToEmbeddable;
import com.djaller.server.email.mapper.SendMailMapper;
import com.djaller.server.email.repo.MailTemplateRepo;
import com.djaller.server.email.repo.SendMailRepo;
import com.djaller.server.email.service.SaveEmailService;
import com.djaller.server.email.service.SendEmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class MailServiceImpl implements SaveEmailService, SendEmailService {

    private final MailTemplateRepo mailTemplateRepo;
    private final SendMailMapper sendMailMapper;
    private final SendMailRepo sendMailRepo;
    private final JavaMailSender emailSender;

    private static InternetAddress[] toAddress(Set<SendToEmbeddable> to) {
        return to
                .stream()
                .map(MailServiceImpl::toToAddress)
                .toArray(InternetAddress[]::new);
    }

    private static InternetAddress toToAddress(SendToEmbeddable sendTo) {
        try {
            return new InternetAddress(sendTo.getEmail(), sendTo.getName());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveEmail(SendMail sendMail) {
        var entity = sendMailMapper.toEntity(sendMail);
        entity.setStatus(SendMailStatus.INIT);
        sendMailRepo.save(entity);
    }

    @Override
    public void sendAllEmail() {
        for (var emailTemplate : EmailTemplate.values()) {
            var sendMailEntities = sendMailRepo
                    .findAllByStatusAndTemplate(SendMailStatus.INIT, emailTemplate);

            if (sendMailEntities.size() == 0) {
                log.debug("No mail to send for emailTemplate {}", emailTemplate);
                continue;
            }

            var templateEntity$ = mailTemplateRepo.findByTemplate(emailTemplate);
            if (templateEntity$.isEmpty()) {
                log.info("Template {} not present. Please create it", emailTemplate);
                continue;
            }

            for (var entity : sendMailEntities) {
                try {
                    sendSingle(entity, templateEntity$.get());
                    entity.setStatus(SendMailStatus.SEND);
                    sendMailRepo.save(entity);
                    log.debug("Email send to {}", entity.getTo());
                } catch (MessagingException | MalformedURLException | UnsupportedEncodingException e) {
                    entity.setStatus(SendMailStatus.ERROR);
                    sendMailRepo.save(entity);
                    log.error("Email not send", e);
                }
            }
        }
    }

    private void sendSingle(SendMailEntity entity, MailTemplateEntity template)
            throws MessagingException, MalformedURLException, UnsupportedEncodingException {
        var message = emailSender.createMimeMessage();

        var helper = new MimeMessageHelper(message, entity.getAttachments().size() != 0);
        helper.setFrom("noreply@djaller.com", "Djaller");
        helper.setTo(toAddress(entity.getTo()));
        helper.setSubject(entity.getSubject());
        helper.setText(template.getPlanText(), template.getHtmlText());

        for (var attachment : entity.getAttachments()) {
            var resource = new UrlResource(URI.create(attachment.getUrl()));
            helper.addAttachment(attachment.getName(), resource, attachment.getContentType());
        }

        emailSender.send(message);
    }
}
