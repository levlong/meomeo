package com.test.demo.exception;

public class DogNotFoundException extends RuntimeException{
    
    public DogNotFoundException(Long id) {
        super("Could not found dog " + id);
    }
}
