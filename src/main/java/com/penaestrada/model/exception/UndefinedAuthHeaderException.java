package com.penaestrada.model.exception;

public class UndefinedAuthHeaderException extends RuntimeException {
    public UndefinedAuthHeaderException(String msg) {
        super(msg);
    }
}
