package com.djaller.server.auth.feign.fallback;

import com.djaller.server.account.model.Account;
import com.djaller.server.account.model.interfaces.*;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class AccountFeignFallback implements CreateAccount, FindAccount, GetAccountByEmail, GetAccountById, UpdateAccount {
    @Override
    public Account createAccount(Account account) {
        return null;
    }

    @Override
    public Collection<Account> find(Collection<String> sort, Integer page, Integer size) {
        return Collections.emptyList();
    }

    @Override
    public Account getAccountByEmail(String email) {
        return null;
    }

    @Override
    public Account getAccountById(UUID accountId) {
        return null;
    }

    @Override
    public Account updateAccount(UUID accountId, Account account) {
        return null;
    }
}
