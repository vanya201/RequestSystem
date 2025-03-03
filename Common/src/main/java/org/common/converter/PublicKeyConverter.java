package org.common.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

@Converter
public class PublicKeyConverter implements AttributeConverter<PublicKey, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(PublicKey publicKey) {
        return publicKey.getEncoded();
    }

    @Override
    public PublicKey convertToEntityAttribute(byte[] dbData) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(dbData);
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert byte[] to PublicKey", e);
        }
    }
}

