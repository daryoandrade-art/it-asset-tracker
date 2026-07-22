package br.com.daryo.it_asset_tracker.exception;

public class ResourceInUseException extends RuntimeException{
    public ResourceInUseException(String message){
        super(message);
    }
}
