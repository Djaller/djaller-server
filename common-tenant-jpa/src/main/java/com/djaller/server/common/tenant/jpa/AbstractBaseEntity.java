package com.djaller.server.common.tenant.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractBaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
