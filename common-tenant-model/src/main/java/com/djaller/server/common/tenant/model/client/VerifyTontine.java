package com.djaller.server.common.tenant.model.client;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FunctionalInterface
public interface VerifyTontine {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/tontines",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    @ResponseBody
    boolean checkIfTontineExist(@RequestParam("name") String name);

}
