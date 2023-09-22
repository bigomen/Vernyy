package com.luxfacta.vernyy.api.exception;

public class BusinessSecurityException extends Exception {
    
    private static final long serialVersionUID = 1L;

    public BusinessSecurityException(String message) {
        super(message);
    }
    
    public BusinessSecurityException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
