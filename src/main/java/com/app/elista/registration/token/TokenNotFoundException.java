package com.app.elista.registration.token;

public class TokenNotFoundException extends Exception {

    public TokenNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
