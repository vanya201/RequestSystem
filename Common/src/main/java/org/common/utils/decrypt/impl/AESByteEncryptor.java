package org.common.utils.decrypt.impl;

import org.common.utils.decrypt.ByteEncryptor;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESByteEncryptor implements ByteEncryptor {


    private final SecretKey secretKey;
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    public AESByteEncryptor(byte[] key) {
        this.secretKey = new SecretKeySpec(key, ALGORITHM);
    }



    @Override
    public byte[] encrypt(byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("Error during encryption", e);
        }
    }



    @Override
    public byte[] decrypt(byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("Error during decryption", e);
        }
    }
}
