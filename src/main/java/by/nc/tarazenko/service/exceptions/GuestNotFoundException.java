package by.nc.tarazenko.service.exceptions;

public class GuestNotFoundException extends RuntimeException {
    public GuestNotFoundException(String messege){
        super(messege);
    }
}
