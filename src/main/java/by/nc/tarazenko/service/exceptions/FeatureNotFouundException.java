package by.nc.tarazenko.service.exceptions;

public class FeatureNotFouundException extends EntityNotFoundException {
    public FeatureNotFouundException(String message){
        super(message);
    }
}
