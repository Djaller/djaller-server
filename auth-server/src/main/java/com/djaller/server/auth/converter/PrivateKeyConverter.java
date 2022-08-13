package com.djaller.server.auth.converter;

import com.djaller.server.auth.util.KeyGenUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Component
@Converter(autoApply = true)
public class PrivateKeyConverter implements AttributeConverter<PrivateKey, String> {

    @Override
    public String convertToDatabaseColumn(PrivateKey attribute) {
        return Base64.getEncoder().encodeToString(attribute.getEncoded());
    }

    @SneakyThrows
    @Override
    public PrivateKey convertToEntityAttribute(String dbData) {
        var bytes = Base64.getDecoder().decode(dbData);
        return KeyGenUtil.keyFactory.generatePrivate(new PKCS8EncodedKeySpec(bytes));
    }
}
