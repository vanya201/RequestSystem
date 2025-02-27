package org.common.utils;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryptor implements Encryptor {

    private final SecretKey secretKey;
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    public AESEncryptor(byte[] key) {
        this.secretKey = new SecretKeySpec(key, ALGORITHM);
    }

    @Override
    public byte[] encrypt(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    @Override
    public byte[] decrypt(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }
}