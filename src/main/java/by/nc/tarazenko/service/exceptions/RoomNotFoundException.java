package by.nc.tarazenko.service.exceptions;

public class RoomNotFoundException extends EntityNotFoundException {
    public RoomNotFoundException(String message){
        super(message);
    }
}
