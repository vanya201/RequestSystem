package org.common.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import org.common.config.EncryptConfig;
import org.common.utils.decrypt.ByteEncryptor;
import java.security.spec.PKCS8EncodedKeySpec;

@Converter
public class PrivateKeyConverter implements AttributeConverter<PrivateKey, byte[]> {

    private final ByteEncryptor encryptUtil = new EncryptConfig().byteEncryptUtil();

    @Override
    public byte[] convertToDatabaseColumn(PrivateKey privateKey) {
        return encryptUtil.encrypt(privateKey.getEncoded());
    }

    @Override
    public PrivateKey convertToEntityAttribute(byte[] bytes) {
        try {
            byte[] decryptedBytes = encryptUtil.decrypt(bytes);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decryptedBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            throw new RuntimeException("Error while decoding private key", e);
        }
    }
}
