package org.example.lesson4_forecast;

public class ResponseException extends Exception{
    private String message;

    public ResponseException(String message) {
        super(message);
        this.message = message;
    }


    @Override
    public String toString() {
        return message;
    }
}
