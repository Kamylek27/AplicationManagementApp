package com.kamil.aplicationmanagementapp.exception;

public class WrongStatusException extends RuntimeException {
    public WrongStatusException() {
        super("Wrong status to change");
    }

}
