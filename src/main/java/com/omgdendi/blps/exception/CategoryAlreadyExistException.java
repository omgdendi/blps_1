package com.omgdendi.blps.exception;

public class CategoryAlreadyExistException extends RuntimeException {
    public CategoryAlreadyExistException() {
        super("Категория уже существует");
    }
}
