package com.cyj.hb.exception;

public class UserNotExistException extends RuntimeException{
    public UserNotExistException(String message) {
        super(message);
    }
}
