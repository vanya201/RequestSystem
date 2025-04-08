package org.friendship.exception;

public class FriendRequestNotFoundException extends RuntimeException {
    public FriendRequestNotFoundException() {
        super("Friend request not found");
    }

    public FriendRequestNotFoundException(String message) {
        super(message);
    }
}
