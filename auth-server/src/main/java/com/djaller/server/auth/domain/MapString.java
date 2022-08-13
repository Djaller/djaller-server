package com.djaller.server.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MapString {

    @NotEmpty
    private String type;

    @NotEmpty
    private String data;
}
