package by.nc.tarazenko.service.exceptions;

public class RoomAlreadyExistException extends  RuntimeException {
    public RoomAlreadyExistException(String message){
        super(message);
    }
}
