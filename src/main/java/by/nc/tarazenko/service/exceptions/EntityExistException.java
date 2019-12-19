package by.nc.tarazenko.service.exceptions;

public class EntityExistException extends RuntimeException{
    public EntityExistException(String message){
        super(message);
    }
}
