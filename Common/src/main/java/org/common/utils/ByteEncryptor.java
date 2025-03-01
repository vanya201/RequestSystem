package org.common.utils;


public interface ByteEncryptor {
    byte[] encrypt(byte[] data);
    byte[] decrypt(byte[] data);
}

