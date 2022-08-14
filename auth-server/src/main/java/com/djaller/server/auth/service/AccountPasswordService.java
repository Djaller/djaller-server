package com.djaller.server.auth.service;

import com.djaller.common.mail.model.EmailHandlerConfig;
import com.djaller.common.mail.model.EmailTemplate;
import com.djaller.common.mail.model.SendMail;
import com.djaller.common.mail.model.SendTo;
import com.djaller.server.account.model.interfaces.GetAccountByEmail;
import com.djaller.server.auth.domain.AccountPasswordCodeEntity;
import com.djaller.server.auth.domain.AccountPasswordEntity;
import com.djaller.server.auth.exception.BadRequestException;
import com.djaller.server.auth.exception.NotFoundException;
import com.djaller.server.auth.repo.AccountPasswordCodeRepo;
import com.djaller.server.auth.repo.AccountPasswordRepo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountPasswordService {

    private static final char[] chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private final GetAccountByEmail getAccountByEmail;
    private final AccountPasswordRepo accountPasswordRepo;
    private final AccountPasswordCodeRepo accountPasswordCodeRepo;
    private final PasswordEncoder passwordEncoder;
    private final StreamBridge streamBridge;

    @Transactional
    protected String createCodeToken(UUID accountId) {
        var code = RandomStringUtils.random(64, 0, chars.length, false, false, chars);

        var entity = new AccountPasswordCodeEntity();
        entity.setCodeHashed(passwordEncoder.encode(code));
        entity.setUsed(false);
        entity.setAccountId(accountId);
        accountPasswordCodeRepo.save(entity);

        return "%s.%s".formatted(entity.getId(), code);
    }

    @Transactional
    protected boolean validateCode(String codeVerifier) {
        String[] split = codeVerifier.split("\\.");
        if (split.length != 2) {
            return false;
        }

        var entity = accountPasswordCodeRepo
                .findById(UUID.fromString(split[0]))
                .orElse(null);
        if (entity == null) {
            return false;
        }

        if (passwordEncoder.matches(split[1], entity.getCodeHashed())) {
            entity.setUsed(true);
            accountPasswordCodeRepo.save(entity);

            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void createPassword(UUID accountId, String value, String codeVerifier) {
        if (!validateCode(codeVerifier)) {
            throw new BadRequestException("invalid_password_code");
        }

        accountPasswordRepo.deactivateAccountPassword(accountId);

        var entity = new AccountPasswordEntity();
        entity.setAccountId(accountId);
        entity.setValue(passwordEncoder.encode(value));
        entity.setActive(true);
        accountPasswordRepo.save(entity);
    }

    public AccountPasswordEntity findLastPassword(UUID accountId) {
        return accountPasswordRepo.findFirstByAccountIdAndActive(accountId, true);
    }

    @Transactional
    public void resetPassword(String username) {
        var account = getAccountByEmail.getAccountByEmail(username);
        if (account == null) {
            throw new NotFoundException();
        }

        accountPasswordCodeRepo.deactivateAccountPassword(account.getId());
        var codeToken = createCodeToken(account.getId());

        var payload = new HashMap<String, Object>();
        payload.put("account", account);
        payload.put("url", ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/passwords/code")
                .queryParam("code", codeToken)
                .toUriString());

        var sendMail = new SendMail();
        sendMail.setTo(Set.of(new SendTo(account.getEmail(), account.getLastName())));
        sendMail.setSubject("Reset password");
        sendMail.setTemplate(EmailTemplate.NEW_PASSWORD);
        sendMail.setConfig(payload);

        streamBridge.send(EmailHandlerConfig.handlerName + "-in-0", sendMail);
    }

    public UUID accountIdForCode(String code) {
        var codeId = code.split("\\.")[0];
        var entity = accountPasswordCodeRepo
                .findById(UUID.fromString(codeId))
                .orElse(null);
        if (entity == null) {
            return null;
        }

        return entity.getAccountId();
    }
}
