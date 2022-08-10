package com.djaller.server.email.service;


import com.djaller.common.mail.model.SendMail;

@FunctionalInterface
public interface SaveEmailService {

    void saveEmail(final SendMail sendMail);

}
