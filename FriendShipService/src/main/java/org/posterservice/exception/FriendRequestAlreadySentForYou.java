package org.posterservice.exception;

public class FriendRequestAlreadySentForYou extends RuntimeException{
    public FriendRequestAlreadySentForYou() {
        super("Friend request already sent");
    }

    public FriendRequestAlreadySentForYou(String message) {
        super(message);
    }
}
