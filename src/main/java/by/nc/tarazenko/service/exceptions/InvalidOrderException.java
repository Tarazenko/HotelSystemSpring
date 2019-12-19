package by.nc.tarazenko.service.exceptions;

public class InvalidOrderException extends BadRequestException{
    public InvalidOrderException(String message){
        super(message);
    }
}
