package com.djaller.common.mail.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class SendTo implements Serializable {
    @NotEmpty
    private String email;

    private String name;
}
