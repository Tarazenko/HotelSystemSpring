package by.nc.tarazenko.service.exceptions;

public class FeatureNotFoundException extends EntityNotFoundException {
    public FeatureNotFoundException(String message){
        super(message);
    }
}
