package by.nc.tarazenko.service.exceptions;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(String message){
        super(message);
    }
}
