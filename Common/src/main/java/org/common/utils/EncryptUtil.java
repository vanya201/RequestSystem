package org.common.utils;

public interface EncryptUtil {
    <T> byte[] encrypt(T data);
    <T> T decrypt(byte[] data);
}
