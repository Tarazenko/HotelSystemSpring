package by.nc.tarazenko.service.exceptions;

public class UserAlreadyExistException extends EntityExistException {
    public UserAlreadyExistException(String message){
        super(message);
    }
}
