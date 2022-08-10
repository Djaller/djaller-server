package com.djaller.server.helper;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Component;

@Component
public class JpaClientRegistrationRepository implements ClientRegistrationRepository {

    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {
        return null;
    }
}
