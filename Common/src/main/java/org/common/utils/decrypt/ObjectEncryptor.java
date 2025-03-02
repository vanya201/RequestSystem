package org.common.utils.decrypt;

public interface ObjectEncryptor {
    <T> byte[] encrypt(T data);
    <T> T decrypt(byte[] data);
}
