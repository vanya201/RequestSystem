package org.common.utils;


public interface Encryptor {
    byte[] encrypt(byte[] data) throws Exception;
    byte[] decrypt(byte[] data) throws Exception;
}

