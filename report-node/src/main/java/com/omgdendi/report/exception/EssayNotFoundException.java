package com.omgdendi.report.exception;

public class EssayNotFoundException extends RuntimeException {
    public EssayNotFoundException() {
        super("Реферат не найден");
    }
}
