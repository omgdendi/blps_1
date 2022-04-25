package com.omgdendi.blps.exception;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException() {
        super("Пользователь с таким именем уже существует");
    }
}
