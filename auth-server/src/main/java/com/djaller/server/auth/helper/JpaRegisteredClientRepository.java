package com.djaller.server.auth.helper;

import com.djaller.server.auth.mapper.RegisteredClientMapper;
import com.djaller.server.auth.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaRegisteredClientRepository implements RegisteredClientRepository {
    private final ClientService clientService;
    private final RegisteredClientMapper registeredClientMapper;

    @Override
    public void save(RegisteredClient registeredClient) {
        var client = registeredClientMapper.toEntity(registeredClient);
        clientService.save(client);
    }

    @Override
    public RegisteredClient findById(String id) {
        return clientService.findById(id)
                .map(registeredClientMapper::toObject)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return clientService.findByClientId(clientId)
                .map(registeredClientMapper::toObject)
                .orElse(null);
    }

}


