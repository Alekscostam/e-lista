package com.app.elista.registration.token;

public class TokenIsExpiredException extends Exception {
    public TokenIsExpiredException(String errorMessage) {
        super(errorMessage);
    }
}
