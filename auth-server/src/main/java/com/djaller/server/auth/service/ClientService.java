package com.djaller.server.auth.service;

import com.djaller.server.auth.exception.NotFoundException;
import com.djaller.server.auth.mapper.RegisteredClientMapper;
import com.djaller.server.auth.repo.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final RegisteredClientMapper registeredClientMapper;

    public RegisteredClient findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return this.clientRepository.findById(id).map(registeredClientMapper::toObject).orElse(null);
    }

    public RegisteredClient findByClientId(String clientId) {
        Assert.hasText(clientId, "clientId cannot be empty");
        return this.clientRepository.findByClientId(clientId).map(registeredClientMapper::toObject).orElse(null);
    }

    public void save(RegisteredClient registeredClient) {
        Assert.notNull(registeredClient, "registeredClient cannot be null");
        this.clientRepository.save(registeredClientMapper.toEntity(registeredClient));
    }

    public void deleteByClientId(String clientId) {
        var client = this.clientRepository.findByClientId(clientId).orElseThrow(NotFoundException::new);
        this.clientRepository.delete(client);
    }
}
