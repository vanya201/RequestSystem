package org.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.common.service.CryptoKeyService;
import org.springframework.stereotype.Component;
import java.security.PublicKey;

@Component
@Converter
@RequiredArgsConstructor
public class PublicKeyConverter implements AttributeConverter<PublicKey, byte[]> {

    private final CryptoKeyService cryptoKeyService;

    @Override
    public byte[] convertToDatabaseColumn(PublicKey publicKey) {
        return cryptoKeyService.encodePublicKey(publicKey);
    }

    @Override
    public PublicKey convertToEntityAttribute(byte[] dbData) {
        return cryptoKeyService.decodePublicKey(dbData);
    }
}

