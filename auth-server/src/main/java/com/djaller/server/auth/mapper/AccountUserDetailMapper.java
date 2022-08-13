package com.djaller.server.auth.mapper;

import com.djaller.server.account.model.Account;
import com.djaller.server.auth.domain.AccountPasswordEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AccountUserDetailMapper {

    public UserDetails toDetail(Account account, AccountPasswordEntity entity) {
        return User
                .builder()
                //
                .accountExpired(false)
                .accountLocked(true)
                .credentialsExpired(false)
                .disabled(false)
                .username(account.getEmail())
                .password(entity.getValue())
                //
                .build()
                ;
    }
}
