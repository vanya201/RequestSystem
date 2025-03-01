package org.common.utils;

public interface ObjectEncryptor {
    <T> byte[] encrypt(T data);
    <T> T decrypt(byte[] data);
}
