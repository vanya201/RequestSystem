package org.common.converter;

import jakarta.persistence.AttributeConverter;
import java.security.PrivateKey;

import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.common.service.CryptoKeyService;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.stereotype.Component;

@Component
@Converter
@RequiredArgsConstructor
public class PrivateKeyConverter implements AttributeConverter<PrivateKey, byte[]> {

    private final BytesEncryptor byteEncryptor;
    private final CryptoKeyService cryptoKeyService;

    @Override
    public byte[] convertToDatabaseColumn(PrivateKey privateKey) {
        byte[] encodeBytes = cryptoKeyService.encodePrivateKey(privateKey);
        return byteEncryptor.encrypt(encodeBytes);
    }

    @Override
    public PrivateKey convertToEntityAttribute(byte[] bytes) {
        byte[] decryptedBytes = byteEncryptor.decrypt(bytes);
        return cryptoKeyService.decodePrivateKey(decryptedBytes);
    }
}
