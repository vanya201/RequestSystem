package org.common.utils;

import java.util.Base64;

/**
 * This class provides functionality for serializing and deserializing data,
 * while also performing encryption and decryption using the AES (Advanced Encryption Standard) algorithm.
 * It ensures that the data is securely transformed into a byte format for storage or transmission,
 * and allows for the secure retrieval and restoration of the original data.
 */

public class EncryptUtilImpl implements EncryptUtil {

    private final DataSerializer dataSerializer = new ObjectDataSerializer();
    private final Encryptor encryptor;

    public EncryptUtilImpl(Encryptor encryptor) {
        this.encryptor = encryptor;
    }


    @Override
    public byte[] encrypt(byte[] data) {
        try {
            return encryptor.encrypt(data);
        } catch (Exception e){
            throw new RuntimeException("Error during encryption", e);
        }
    }



    @Override
    public byte[] decrypt(byte[] data) {
        try {
            return encryptor.decrypt(data);
        }catch (Exception e){
            throw new RuntimeException("Error during decryption", e);
        }
    }



    @Override
    public String encrypt(String data) {
        try {
            byte[] dataBytes = Base64.getEncoder().encode(data.getBytes());
            byte[] encryptedData = encryptor.encrypt(dataBytes);
            return java.util.Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Error during string encryption", e);
        }
    }



    @Override
    public String decrypt(String data) {
        try {
            byte[] encryptedData = Base64.getDecoder().decode(data);
            byte[] decryptedData = encryptor.decrypt(encryptedData);
            return new String(Base64.getDecoder().decode(decryptedData));
        } catch (Exception e) {
            throw new RuntimeException("Error during string decryption", e);
        }
    }



    public <T> byte[] encryptObject(T data) {
        try {
            byte[] serializedData = dataSerializer.serialize(data);
            return encryptor.encrypt(serializedData);
        } catch (Exception e) {
            throw new RuntimeException("Error during encryption", e);
        }
    }



    public <T> T decryptObject(byte[] data) {
        try {
            byte[] decryptedData = encryptor.decrypt(data);
            return dataSerializer.deserialize(decryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Error during decryption", e);
        }
    }
}
