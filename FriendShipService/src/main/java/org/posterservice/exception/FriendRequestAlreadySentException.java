package org.posterservice.exception;

public class FriendRequestAlreadySentException extends RuntimeException {
    public FriendRequestAlreadySentException() {
        super("Friend request already sent");
    }

    public FriendRequestAlreadySentException(String message) {
        super(message);
    }
}