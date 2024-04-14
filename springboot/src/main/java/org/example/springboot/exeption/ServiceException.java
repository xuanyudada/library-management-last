package org.example.springboot.exeption;

public class ServiceException extends RuntimeException{
    private String code;

    public String getCode() {
        return code;
    }

    public ServiceException(String code, String message) {
        super(message);
        this.code=code;
    }


    public ServiceException(String message) {
        super(message);
    }
}
