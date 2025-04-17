package org.common.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("Пользователь с именем '" + username + "' не найден");
    }
}
