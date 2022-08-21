package com.djaller.server.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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
    @Column(columnDefinition = "text")
    private String data;
}
