package org.common.utils.decrypt;

public interface StringEncryptor {
    String encrypt(String data);
    String decrypt(String data);
}
