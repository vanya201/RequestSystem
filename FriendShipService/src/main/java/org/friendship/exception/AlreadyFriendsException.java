package org.friendship.exception;

public class AlreadyFriendsException extends RuntimeException {
    public AlreadyFriendsException() {
        super("You are already friends with this user");
    }

    public AlreadyFriendsException(String message) {
        super(message);
    }
}
