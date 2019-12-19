package by.nc.tarazenko.service.exceptions;

public class RoomAlreadyExistException extends  EntityExistException {
    public RoomAlreadyExistException(String message){
        super(message);
    }
}
