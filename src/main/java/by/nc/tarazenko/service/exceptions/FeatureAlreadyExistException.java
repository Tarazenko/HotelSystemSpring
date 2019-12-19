package by.nc.tarazenko.service.exceptions;

public class FeatureAlreadyExistException extends EntityExistException {
    public FeatureAlreadyExistException(String message){
        super(message);
    }
}
