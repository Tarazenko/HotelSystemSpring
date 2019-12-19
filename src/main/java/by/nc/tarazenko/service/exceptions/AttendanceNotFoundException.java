package by.nc.tarazenko.service.exceptions;

public class AttendanceNotFoundException extends EntityNotFoundException{
    public AttendanceNotFoundException(String message){
        super(message);
    }
}
