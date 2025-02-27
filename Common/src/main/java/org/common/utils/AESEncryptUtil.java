package org.common.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;

/**
 * This class provides functionality for serializing and deserializing data,
 * while also performing encryption and decryption using the AES (Advanced Encryption Standard) algorithm.
 * It ensures that the data is securely transformed into a byte format for storage or transmission,
 * and allows for the secure retrieval and restoration of the original data.
 */

public class AESEncryptUtil implements EncryptUtil{

    private final DataSerializer dataSerializer;
    private final Encryptor encryptor;

    public AESEncryptUtil(byte[] key) {
        this.dataSerializer = new ObjectDataSerializer();
        this.encryptor = new AESEncryptor(key);
    }


    public <T> byte[] encrypt(T data) {
        try {
            byte[] serializedData = dataSerializer.serialize(data);
            return encryptor.encrypt(serializedData);
        } catch (Exception e) {
            throw new RuntimeException("Error during encryption", e);
        }
    }


    public <T> T decrypt(byte[] data) {
        try {
            byte[] decryptedData = encryptor.decrypt(data);
            return dataSerializer.deserialize(decryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Error during decryption", e);
        }
    }
}
