package by.nc.tarazenko.service.exceptions;

public class AttendanceNotFoundException extends RuntimeException {
    public AttendanceNotFoundException(String message){
        super(message);
    }
}
