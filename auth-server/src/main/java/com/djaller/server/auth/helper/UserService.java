package com.djaller.server.auth.helper;

import com.djaller.server.account.model.interfaces.GetAccountByEmail;
import com.djaller.server.auth.mapper.AccountUserDetailMapper;
import com.djaller.server.auth.service.AccountPasswordService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserService implements UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final GetAccountByEmail getAccount;
    private final AccountUserDetailMapper mapper;
    private final AccountPasswordService passwordService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            var account = getAccount.getAccountByEmail(username);
            if (account == null) {
                log.info("account not found [{}]", username);
                throw new UsernameNotFoundException("Account [%s] not found".formatted(username));
            }

            var password = passwordService.findLastPassword(account.getId());
            return mapper.toDetail(account, password);
        } catch (FeignException feignException) {
            log.info("Cannot find account [%s]".formatted(username), feignException);
            throw new UsernameNotFoundException("Cannot find account [%s]".formatted(username), feignException);
        }
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("TODO Should test this: \n{} - \n{}", userRequest.getAccessToken(), userRequest.getClientRegistration());
        return null;
    }
}
