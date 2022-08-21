package com.djaller.server.auth.mapper;

import com.djaller.server.account.model.Account;
import com.djaller.server.account.model.AccountStatus;
import com.djaller.server.auth.domain.AccountPasswordEntity;
import com.djaller.server.auth.exception.BadRequestException;
import com.djaller.server.auth.model.CurrentUser;
import com.djaller.server.auth.model.oauth.OAuth2UserInfo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AccountUserDetailMapper {

    public OAuth2UserInfo toOAuth2UserInfo(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        throw new BadRequestException("Not implemented");
    }

    public CurrentUser toDetail(Account account, AccountPasswordEntity entity) {
        return CurrentUser
                .builder()
                .id(account.getId())
                .email(account.getEmail())
                .authorities(new HashSet<>(Set.of(new SimpleGrantedAuthority("USER"))))
                .attributes(accountToClaims(account))
                .active(account.getStatus() == AccountStatus.ACTIVE)
                .password(entity != null ? entity.getValue() : null)
                .build();
    }

    private Map<String, Object> accountToClaims(Account account) {
        var tmp = new HashMap<String, Object>();
        tmp.put("sub", account.getEmail());
        tmp.put("name", "%s %s".formatted(account.getFirstName(), account.getLastName()).trim());
        tmp.put("family_name", account.getFirstName());
        tmp.put("middle_name", account.getLastName());
        tmp.put("picture", account.getPictureUrl());
        tmp.put("email", account.getEmail());
        tmp.put("email_verified", account.getEmailVerified());
        tmp.put("locale", account.getLocale());
        tmp.put("phone_number", account.getPhoneNumber());

        var result = new HashMap<String, Object>();
        tmp.forEach((k, v) -> {
            if (k != null && v != null) {
                result.put(k, v);
            }
        });
        return result;
    }
}
