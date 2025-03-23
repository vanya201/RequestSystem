package org.common.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FriendPair implements Serializable {
    String sender;
    String receiver;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendPair that = (FriendPair) o;
        return (sender.equals(that.sender) && receiver.equals(that.receiver)) ||
                (sender.equals(that.receiver) && receiver.equals(that.sender));
    }

    @Override
    public int hashCode() {
        return sender.hashCode() + receiver.hashCode();
    }
}

