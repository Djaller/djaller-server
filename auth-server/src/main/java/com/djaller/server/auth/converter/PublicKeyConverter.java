package com.djaller.server.auth.converter;

import com.djaller.server.auth.util.KeyGenUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
@Converter(autoApply = true)
public class PublicKeyConverter implements AttributeConverter<PublicKey, String> {

    @Override
    public String convertToDatabaseColumn(PublicKey attribute) {
        return Base64.getEncoder().encodeToString(attribute.getEncoded());
    }

    @SneakyThrows
    @Override
    public PublicKey convertToEntityAttribute(String dbData) {
        var bytes = Base64.getDecoder().decode(dbData);
        return KeyGenUtil.keyFactory.generatePublic(new X509EncodedKeySpec(bytes));
    }
}
