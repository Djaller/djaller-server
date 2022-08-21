package com.djaller.server.auth.helper;

import com.djaller.server.account.model.interfaces.GetAccountByEmail;
import com.djaller.server.auth.mapper.AccountUserDetailMapper;
import com.djaller.server.auth.model.CurrentUser;
import com.djaller.server.auth.service.AccountPasswordService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserService extends DefaultOAuth2UserService implements UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final GetAccountByEmail getAccount;
    // private final CreateAccount createAccount;
    // private final UpdateAccount updateAccount;
    private final AccountUserDetailMapper mapper;
    private final AccountPasswordService passwordService;

    @Override
    public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
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
    public CurrentUser loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        var oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private CurrentUser processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        // var oAuth2UserInfo = mapper.toOAuth2UserInfo(oAuth2UserRequest, oAuth2User);
        // TODO
        return null;
    }
}
