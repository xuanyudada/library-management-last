package org.example.springboot.exeption;

public class ServiceException extends RuntimeException{

    public ServiceException(String message) {
        super(message);
    }
}
