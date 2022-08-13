package com.djaller.server.auth.service;

import com.djaller.server.auth.SimpleClientRegistration;
import com.djaller.server.auth.exception.NotFoundException;
import com.djaller.server.auth.mapper.ClientRegistrationMapper;
import com.djaller.server.auth.repo.ClientRegistrationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientRegistrationService {
    private final ClientRegistrationRepo registrationRepo;
    private final ClientRegistrationMapper registrationMapper;

    public ClientRegistration findByRegistrationId(String registrationId) {
        try {
            return registrationRepo
                    .findByRegistrationId(registrationId)
                    .map(registrationMapper::toModel)
                    .orElse(null);
        } catch (Exception e) {
            log.error("Could not resolve client by registration [%s]".formatted(registrationId), e);
            return null;
        }
    }

    public ClientRegistration save(ClientRegistration clientRegistration) {
        var mapped = registrationMapper.toEntity(clientRegistration);
        var saved = registrationRepo.save(mapped);
        return registrationMapper.toModel(saved);
    }

    public Collection<SimpleClientRegistration> listClientRegistration() {
        throw new RuntimeException("TODO");
    }

    public void deleteByRegistrationId(String registrationId) {
        var entity = this.registrationRepo.findByRegistrationId(registrationId).orElseThrow(NotFoundException::new);
        this.registrationRepo.delete(entity);
    }
}
