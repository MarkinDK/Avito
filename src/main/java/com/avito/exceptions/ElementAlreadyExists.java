package com.avito.exceptions;

public class ElementAlreadyExists extends RuntimeException {
    public ElementAlreadyExists(String message) {
        super(message);
    }
}
