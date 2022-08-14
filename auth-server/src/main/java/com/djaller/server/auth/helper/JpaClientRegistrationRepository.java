package com.djaller.server.auth.helper;

import com.djaller.server.auth.service.ProviderClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JpaClientRegistrationRepository implements ClientRegistrationRepository {

    private final ProviderClientService providerClientService;

    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {
        return providerClientService.findByRegistrationId(registrationId);
    }
}
