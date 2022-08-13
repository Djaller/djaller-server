package com.djaller.server.mainapi.mapper;

import com.djaller.server.account.model.Account;
import com.djaller.server.mainapi.domain.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {
    AccountEntity toEntity(Account model);

    Account toModel(AccountEntity entity);
}
