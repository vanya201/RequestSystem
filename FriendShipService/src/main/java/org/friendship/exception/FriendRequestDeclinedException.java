package org.friendship.exception;

public class FriendRequestDeclinedException extends RuntimeException{
    public FriendRequestDeclinedException() {
        super("Friend request already declined");
    }

    public FriendRequestDeclinedException(String message) {
        super(message);
    }
}
