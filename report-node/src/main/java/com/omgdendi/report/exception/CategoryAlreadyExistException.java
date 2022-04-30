package com.omgdendi.report.exception;

public class CategoryAlreadyExistException extends RuntimeException {
    public CategoryAlreadyExistException() {
        super("Категория уже существует");
    }
}
