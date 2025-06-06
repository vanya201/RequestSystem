package org.friendship.exception;

public class UserNotFound extends RuntimeException {
    public UserNotFound() {
        super("User not found");
    }

    public UserNotFound(String message) {
        super(message);
    }
}
