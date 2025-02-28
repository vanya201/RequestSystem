package org.common.utils;

public interface EncryptUtil {

    byte[] encrypt(byte[] data);
    byte[] decrypt(byte[] data);

    String encrypt(String data);
    String decrypt(String data);

    <T> byte[] encryptObject(T data);
    <T> T decryptObject(byte[] data);
}

