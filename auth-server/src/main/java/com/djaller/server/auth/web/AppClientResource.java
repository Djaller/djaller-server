package com.djaller.server.auth.web;

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

@Tag(name = "app-client")
@Slf4j
@RestController
@RequestMapping("/api/app-clients")
@RequiredArgsConstructor
public class AppClientResource {

    private final ClientService clientService;

    @GetMapping
    public Collection<AppClient> listAppClient() {
        return clientService.listClientService();
    }

    @GetMapping("{clientId}")
    public AppClient getAppClientByClientId(@PathVariable String clientId) {
        return clientService.getAppClientByClientId(clientId);
    }

    @Transactional
    @PostMapping
    public AppClient saveAppClient(@RequestBody @Valid AppClient client) {
        return clientService.save(client);
    }

    @Transactional
    @PutMapping("{clientId}")
    public AppClient updateAppClient(@PathVariable String clientId, @RequestBody @Valid AppClient client) {
        return clientService.updateAppClient(clientId, client);
    }

    @DeleteMapping("by-client-id/{clientId}")
    public void deleteProviderClient(@PathVariable String clientId) {
        clientService.deleteByClientId(clientId);
    }

    @Transactional
    @PutMapping("secret/{clientId}")
    public AppClient updateSecretAppClient(@PathVariable String clientId, @RequestBody @Valid AppClientSecretUpdate update) {
        return clientService.updateAppClientSecret(clientId, update.getSecret());
    }
}
