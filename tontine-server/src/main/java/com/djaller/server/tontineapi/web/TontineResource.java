package com.djaller.server.tontineapi.web;

import com.djaller.server.common.tenant.model.client.VerifyTontine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TontineResource implements VerifyTontine {
    @Override
    public boolean checkIfTontineExist(String name) {
        return false;
    }
}
