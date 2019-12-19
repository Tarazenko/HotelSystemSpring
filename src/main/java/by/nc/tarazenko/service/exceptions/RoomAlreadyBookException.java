package by.nc.tarazenko.service.exceptions;

public class RoomAlreadyBookException extends EntityExistException {
    public RoomAlreadyBookException(String message){
        super(message);
    }
}
