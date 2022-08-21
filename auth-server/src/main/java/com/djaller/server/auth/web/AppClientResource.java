package com.djaller.server.auth.web;

import com.djaller.server.auth.domain.AppClientEntity;
import com.djaller.server.auth.mapper.AppClientMapper;
import com.djaller.server.auth.model.AppClient;
import com.djaller.server.auth.model.AppClientSecretUpdate;
import com.djaller.server.auth.service.ClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@Tag(name = "app-client")
@Slf4j
@RestController
@RequestMapping("/auth/api/app-clients")
@RequiredArgsConstructor
public class AppClientResource {

    private final ClientService clientService;
    private final AppClientMapper appClientMapper;

    @GetMapping
    public Collection<AppClient> listAppClient() {
        return clientService
                .listClientService()
                .stream().map(appClientMapper::toModel)
                .collect(Collectors.toSet());
    }

    @GetMapping("{clientId}")
    public AppClient getAppClientByClientId(@PathVariable String clientId) {
        AppClientEntity entity = clientService.getAppClientByClientId(clientId);
        return appClientMapper.toModel(entity);
    }

    @Transactional
    @PostMapping
    public AppClient saveAppClient(@RequestBody @Valid AppClient client) {
        var entity = appClientMapper.toEntity(client);
        var saved = clientService.save(entity);
        return appClientMapper.toModel(saved);
    }

    @Transactional
    @PutMapping("{clientId}")
    public AppClient updateAppClient(@PathVariable String clientId, @RequestBody @Valid AppClient client) {
        var entity = appClientMapper.toEntity(client);
        var saved = clientService.updateAppClient(clientId, entity);
        return appClientMapper.toModel(saved);
    }

    @DeleteMapping("by-client-id/{clientId}")
    public void deleteProviderClient(@PathVariable String clientId) {
        clientService.deleteByClientId(clientId);
    }

    @Transactional
    @PutMapping("secret/{clientId}")
    public AppClient updateSecretAppClient(@PathVariable String clientId, @RequestBody @Valid AppClientSecretUpdate update) {
        var client = clientService.updateAppClientSecret(clientId, update.getSecret());
        return appClientMapper.toModel(client);
    }
}
