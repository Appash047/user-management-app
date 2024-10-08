package com.gokulinfocare.userapp.exception;

public class UserNotFoundException extends BadDataException {

    public UserNotFoundException(String message, String redirectUrl) {
        super(message, redirectUrl);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

}
