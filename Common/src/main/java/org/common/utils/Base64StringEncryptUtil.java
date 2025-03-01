package org.common.utils;

import java.util.Base64;

public class Base64StringEncryptUtil implements StringEncryptor {

    private final ByteEncryptor byteEncryptor;

    public Base64StringEncryptUtil(ByteEncryptor byteEncryptor) {
        this.byteEncryptor = byteEncryptor;
    }



    @Override
    public String encrypt(String data) {
        try {
            byte[] dataBytes = Base64.getEncoder().encode(data.getBytes());
            byte[] encryptedData = byteEncryptor.encrypt(dataBytes);
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Error during string encryption", e);
        }
    }



    @Override
    public String decrypt(String data) {
        try {
            byte[] encryptedData = Base64.getDecoder().decode(data);
            byte[] decryptedData = byteEncryptor.decrypt(encryptedData);
            return new String(Base64.getDecoder().decode(decryptedData));
        } catch (Exception e) {
            throw new RuntimeException("Error during string decryption", e);
        }
    }
}
