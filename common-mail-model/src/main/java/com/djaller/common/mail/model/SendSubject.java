package com.djaller.common.mail.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SendSubject implements Serializable {
    private String value;
    private String key;
}
