package com.djaller.server.auth.repo;

import com.djaller.server.auth.core.BaseRepo;
import com.djaller.server.auth.domain.AuthorizationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorizationRepository extends BaseRepo<AuthorizationEntity, String> {
    Optional<AuthorizationEntity> findByState(String state);

    Optional<AuthorizationEntity> findByAuthorizationCodeValue(String authorizationCode);

    Optional<AuthorizationEntity> findByAccessTokenValue(String accessToken);

    Optional<AuthorizationEntity> findByRefreshTokenValue(String refreshToken);

    @Query("select a from AuthorizationEntity a where a.state = :token" +
            " or a.authorizationCodeValue = :token" +
            " or a.accessTokenValue = :token" +
            " or a.refreshTokenValue = :token"
    )
    Optional<AuthorizationEntity> findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValue(@Param("token") String token);
}
