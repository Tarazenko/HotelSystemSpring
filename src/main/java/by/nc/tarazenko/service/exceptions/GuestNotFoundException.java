package by.nc.tarazenko.service.exceptions;

public class GuestNotFoundException extends EntityNotFoundException {
    public GuestNotFoundException(String messege){
        super(messege);
    }
}
