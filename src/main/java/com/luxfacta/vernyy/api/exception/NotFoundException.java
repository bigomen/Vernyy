package com.luxfacta.vernyy.api.exception;

public class NotFoundException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    public NotFoundException() {
        super("ID nao encontrado.");
    }
    
}
