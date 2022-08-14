package com.djaller.server.auth.service;

import com.djaller.server.auth.exception.NotFoundException;
import com.djaller.server.auth.mapper.AppClientMapper;
import com.djaller.server.auth.mapper.RegisteredClientMapper;
import com.djaller.server.auth.model.AppClient;
import com.djaller.server.auth.repo.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final AppClientMapper appClientMapper;
    private final ClientRepository clientRepository;
    private final RegisteredClientMapper registeredClientMapper;
    private final PasswordEncoder passwordEncoder;

    public RegisteredClient findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return this.clientRepository.findById(id).map(registeredClientMapper::toObject).orElse(null);
    }

    public RegisteredClient findByClientId(String clientId) {
        Assert.hasText(clientId, "clientId cannot be empty");
        return this.clientRepository.findByClientId(clientId).map(registeredClientMapper::toObject).orElse(null);
    }

    public AppClient save(AppClient client) {
        var entity = appClientMapper.toEntity(client);
        var saved = clientRepository.save(entity);
        return appClientMapper.toModel(saved);
    }

    public void save(RegisteredClient registeredClient) {
        Assert.notNull(registeredClient, "registeredClient cannot be null");
        this.clientRepository.save(registeredClientMapper.toEntity(registeredClient));
    }

    public void deleteByClientId(String clientId) {
        var client = this.clientRepository.findByClientId(clientId).orElseThrow(NotFoundException::new);
        this.clientRepository.delete(client);
    }

    public Collection<AppClient> listClientService() {
        return clientRepository.findAll().stream().map(appClientMapper::toModel).collect(Collectors.toSet());
    }

    public AppClient updateAppClient(String clientId, AppClient client) {
        var found = this.clientRepository.findByClientId(clientId).orElseThrow(NotFoundException::new);
        var mapped = appClientMapper.toEntity(client);
        BeanUtils.copyProperties(mapped, found);
        var saved = clientRepository.save(found);
        return appClientMapper.toModel(saved);
    }

    public AppClient getAppClientByClientId(String clientId) {
        return this.clientRepository.findByClientId(clientId).map(appClientMapper::toModel).orElseThrow(NotFoundException::new);
    }

    public AppClient updateAppClientSecret(String clientId, String secret) {
        var entity = this.clientRepository.findByClientId(clientId).orElseThrow(NotFoundException::new);
        entity.setClientSecret(passwordEncoder.encode(secret));
        var saved = clientRepository.save(entity);
        return appClientMapper.toModel(saved);
    }
}
