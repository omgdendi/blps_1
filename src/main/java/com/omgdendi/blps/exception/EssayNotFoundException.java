package com.omgdendi.blps.exception;

public class EssayNotFoundException extends RuntimeException {
    public EssayNotFoundException() {
        super("Реферат не найден");
    }
}
