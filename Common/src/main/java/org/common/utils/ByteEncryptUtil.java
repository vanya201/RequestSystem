package org.common.utils;

public class ByteEncryptUtil implements ByteEncryptor {
    private final ByteEncryptor byteEncryptor;

    public ByteEncryptUtil(ByteEncryptor byteEncryptor) {
        this.byteEncryptor = byteEncryptor;
    }



    @Override
    public byte[] encrypt(byte[] data) {
        try {
            return byteEncryptor.encrypt(data);
        } catch (Exception e){
            throw new RuntimeException("Error during encryption", e);
        }
    }



    @Override
    public byte[] decrypt(byte[] data) {
        try {
            return byteEncryptor.decrypt(data);
        }catch (Exception e){
            throw new RuntimeException("Error during decryption", e);
        }
    }
}
