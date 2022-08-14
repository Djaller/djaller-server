package com.djaller.server.auth.service;

import com.djaller.server.auth.exception.NotFoundException;
import com.djaller.server.auth.mapper.ClientRegistrationMapper;
import com.djaller.server.auth.mapper.SimpleClientRegistrationMapper;
import com.djaller.server.auth.model.ProviderClientModel;
import com.djaller.server.auth.model.SimpleProviderClientModel;
import com.djaller.server.auth.repo.ClientRegistrationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProviderClientService {
    private final ClientRegistrationRepo registrationRepo;
    private final ClientRegistrationMapper registrationMapper;
    private final SimpleClientRegistrationMapper simpleClientRegistrationMapper;

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
        return save(clientRegistration, false);
    }

    public ClientRegistration save(ClientRegistration clientRegistration, Boolean systemClient) {
        var mapped = registrationMapper.toEntity(clientRegistration);
        mapped.setSystemClient(systemClient);
        var saved = registrationRepo.save(mapped);
        return registrationMapper.toModel(saved);
    }

    public ProviderClientModel save(ProviderClientModel clientRegistration) {
        var mapped = registrationMapper.toClientRegistration(clientRegistration);
        var saved = registrationRepo.save(mapped);
        return registrationMapper.toClientRegistrationModel(saved);
    }

    public SimpleProviderClientModel save(SimpleProviderClientModel clientRegistration) {
        var mapped = simpleClientRegistrationMapper.toEntity(clientRegistration);
        var saved = registrationRepo.save(mapped);
        return simpleClientRegistrationMapper.toModel(saved);
    }

    public Collection<SimpleProviderClientModel> listClientRegistration() {
        return registrationRepo.findBySystemClient(false)
                .stream()
                .map(simpleClientRegistrationMapper::toModel)
                .collect(Collectors.toSet());
    }

    public Collection<SimpleProviderClientModel> listAllClientRegistration() {
        return registrationRepo.findAll()
                .stream()
                .map(simpleClientRegistrationMapper::toModel)
                .collect(Collectors.toSet());
    }

    public void deleteByRegistrationId(String registrationId) {
        var entity = this.registrationRepo.findByRegistrationId(registrationId).orElseThrow(NotFoundException::new);
        this.registrationRepo.delete(entity);
    }
}
