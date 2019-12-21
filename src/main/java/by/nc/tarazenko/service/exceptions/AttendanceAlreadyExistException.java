package by.nc.tarazenko.service.exceptions;

public class AttendanceAlreadyExistException extends EntityExistException {
    public AttendanceAlreadyExistException(String message){
        super(message);
    }
}
