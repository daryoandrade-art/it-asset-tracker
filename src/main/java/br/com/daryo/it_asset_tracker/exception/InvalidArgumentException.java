package br.com.daryo.it_asset_tracker.exception;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(String message){
        super(message);
    }
}
