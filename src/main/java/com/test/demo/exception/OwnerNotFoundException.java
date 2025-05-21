package com.test.demo.exception;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException(Long id) {
        super("Owner Not Found " + id);
    }
    
}
