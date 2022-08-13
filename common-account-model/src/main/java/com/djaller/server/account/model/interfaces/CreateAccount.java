package com.djaller.server.account.model.interfaces;

import com.djaller.server.account.model.Account;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FunctionalInterface
public interface CreateAccount {

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/accounts",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    Account createAccount(@RequestBody Account account);

}
