package com.djaller.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`authorizationConsent`")
@IdClass(AuthorizationConsent.AuthorizationConsentId.class)
public class AuthorizationConsent {

    @Id
    private String registeredClientId;

    @Id
    private String principalName;

    @Column(length = 1000)
    private String authorities;

    public static class AuthorizationConsentId implements Serializable {
        private String registeredClientId;
        private String principalName;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (!(o instanceof AuthorizationConsentId that)) return false;

            return new EqualsBuilder().append(registeredClientId, that.registeredClientId).append(principalName, that.principalName).isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37).append(registeredClientId).append(principalName).toHashCode();
        }
    }
}
