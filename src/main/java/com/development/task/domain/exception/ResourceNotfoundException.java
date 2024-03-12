package com.development.task.domain.exception;

public class ResourceNotfoundException extends RuntimeException{
    public ResourceNotfoundException(String message){
        super(message);
    }
}
