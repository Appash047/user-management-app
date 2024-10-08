package com.gokulinfocare.userapp.exception;

public class BadDataException extends RuntimeException {

    private String redirectUrl;

    public BadDataException(String message, String redirectUrl) {
        super(message);
        this.redirectUrl = redirectUrl;
    }

    public BadDataException(String message) {
        super(message);
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

}
