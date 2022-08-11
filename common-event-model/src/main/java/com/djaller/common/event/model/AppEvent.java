package com.djaller.common.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppEvent implements Serializable {
    private String eventType;
    private UUID tontineId;
    private Object payload;
}
