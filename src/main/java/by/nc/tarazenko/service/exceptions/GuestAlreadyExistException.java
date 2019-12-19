package by.nc.tarazenko.service.exceptions;

public class GuestAlreadyExistException extends EntityExistException {
    public GuestAlreadyExistException(String message){
        super(message);
    }
}
