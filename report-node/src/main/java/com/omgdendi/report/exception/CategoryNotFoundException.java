package com.omgdendi.report.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException() {
        super("Категория не найдена");
    }
}