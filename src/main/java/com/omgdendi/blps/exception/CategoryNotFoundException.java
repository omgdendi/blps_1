package com.omgdendi.blps.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException() {
        super("Категория не найдена");
    }
}