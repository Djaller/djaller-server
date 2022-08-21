package com.djaller.server.auth.service;

import com.djaller.server.auth.domain.AppClientEntity;
import com.djaller.server.auth.exception.NotFoundException;
import com.djaller.server.auth.repo.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<AppClientEntity> findById(String id) {
        return this.clientRepository.findById(id);
    }

    public Optional<AppClientEntity> findByClientId(String clientId) {
        Assert.hasText(clientId, "clientId cannot be empty");
        return this.clientRepository.findByClientId(clientId);
    }

    public AppClientEntity save(AppClientEntity client) {
        return clientRepository.save(client);
    }

    public void deleteByClientId(String clientId) {
        var client = this.clientRepository.findByClientId(clientId).orElseThrow(NotFoundException::new);
        this.clientRepository.delete(client);
    }

    public Collection<AppClientEntity> listClientService() {
        return clientRepository.findAll();
    }

    public AppClientEntity updateAppClient(String clientId, AppClientEntity client) {
        var found = this.clientRepository.findByClientId(clientId).orElseThrow(NotFoundException::new);
        BeanUtils.copyProperties(client, found);
        return clientRepository.save(found);
    }

    public AppClientEntity getAppClientByClientId(String clientId) {
        return this.clientRepository.findByClientId(clientId).orElseThrow(NotFoundException::new);
    }

    public AppClientEntity updateAppClientSecret(String clientId, String secret) {
        var entity = this.clientRepository.findByClientId(clientId).orElseThrow(NotFoundException::new);
        entity.setClientSecret(passwordEncoder.encode(secret));
        return clientRepository.save(entity);
    }
}
