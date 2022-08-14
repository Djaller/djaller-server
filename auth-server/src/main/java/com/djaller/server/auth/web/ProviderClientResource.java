package com.djaller.server.auth.web;

import com.djaller.server.auth.model.ProviderClientModel;
import com.djaller.server.auth.model.SimpleProviderClientModel;
import com.djaller.server.auth.service.ProviderClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collection;

@Tag(name = "provider-client")
@Slf4j
@RestController
@RequestMapping("/api/provider-clients")
@RequiredArgsConstructor
public class ProviderClientResource {

    private final ProviderClientService providerClientService;

    @GetMapping
    public Collection<SimpleProviderClientModel> listProviderClient() {
        return providerClientService.listClientRegistration();
    }

    @GetMapping("and-systems")
    public Collection<SimpleProviderClientModel> listAllProviderClient() {
        return providerClientService.listAllClientRegistration();
    }

    @Transactional
    @PostMapping
    public ProviderClientModel saveProviderClient(@RequestBody @Valid ProviderClientModel client) {
        return providerClientService.save(client);
    }

    @DeleteMapping("by-registration/{registrationId}")
    public void deleteProviderClient(@PathVariable String registrationId) {
        providerClientService.deleteByRegistrationId(registrationId);
    }

}
