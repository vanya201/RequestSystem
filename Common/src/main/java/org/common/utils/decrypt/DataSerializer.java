package org.common.utils.decrypt;

import java.io.IOException;

public interface DataSerializer {
    <T> byte[] serialize(T data) throws IOException;
    <T> T deserialize(byte[] data) throws IOException, ClassNotFoundException;
}

