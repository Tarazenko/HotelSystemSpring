package by.nc.tarazenko.service.exceptions;

public class RoomAlreadyBookException extends RuntimeException {
    public RoomAlreadyBookException(String message){
        super(message);
    }
}
