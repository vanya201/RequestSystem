package org.posterservice.exception;

public class FriendRequestAlreadyAcceptException extends RuntimeException {
    public FriendRequestAlreadyAcceptException() {
        super("Friend request already accept");
    }

    public FriendRequestAlreadyAcceptException(String message) {
        super(message);
    }
}
