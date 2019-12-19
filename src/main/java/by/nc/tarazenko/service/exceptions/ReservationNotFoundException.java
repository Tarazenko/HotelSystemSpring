package by.nc.tarazenko.service.exceptions;

public class ReservationNotFoundException extends EntityNotFoundException {
    public ReservationNotFoundException(String message){
        super(message);
    }
}
