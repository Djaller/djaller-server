package com.djaller.server.auth.helper;

import com.djaller.server.auth.exception.NotFoundException;
import com.djaller.server.auth.service.ClientRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JpaClientRegistrationRepository implements ClientRegistrationRepository {

    private final ClientRegistrationService clientRegistrationService;

    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {
        return clientRegistrationService.findByRegistrationId(registrationId);
    }
}
