package org.common.utils;

public class ObjectEncryptUtil implements ObjectEncryptor {
    private final ByteEncryptor byteEncryptor;
    private final DataSerializer dataSerializer;

    public ObjectEncryptUtil(ByteEncryptor byteEncryptor, DataSerializer dataSerializer) {
        this.byteEncryptor = byteEncryptor;
        this.dataSerializer = dataSerializer;
    }



    @Override
    public <T> byte[] encrypt(T data) {
        try {
            byte[] serializedData = dataSerializer.serialize(data);
            return byteEncryptor.encrypt(serializedData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public <T> T decrypt(byte[] data) {
        try {
            byte[] decryptedData = byteEncryptor.decrypt(data);
            return dataSerializer.deserialize(decryptedData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
