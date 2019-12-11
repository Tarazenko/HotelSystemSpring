package by.nc.tarazenko.controller;

import by.nc.tarazenko.entity.Error;
import by.nc.tarazenko.service.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler({GuestNotFoundException.class, MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class, AttendanceNotFoundException.class,
            RoomNotFoundException.class, FeatureNotFouundException.class,
            ReservationNotFoundException.class, DateTimeParseException.class,
            InvalidOrderException.class, RoomAlreadyBookException.class,
            RoomAlreadyExistException.class
    })
    public final ResponseEntity<Error> handleException(Exception e, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        if (e instanceof GuestNotFoundException) {
            return handleGuestNotFoundException
                    ((GuestNotFoundException) e, headers, HttpStatus.NOT_FOUND, request);
        } else if (e instanceof AttendanceNotFoundException) {
            return handleAttendanceNotFoundException
                    ((AttendanceNotFoundException) e, headers, HttpStatus.NOT_FOUND, request);
        } else if (e instanceof RoomNotFoundException) {
            return handleRoomNotFoundException
                    ((RoomNotFoundException) e, headers, HttpStatus.NOT_FOUND, request);
        } else if (e instanceof FeatureNotFouundException) {
            return handleFeatureNotFoundException
                    ((FeatureNotFouundException) e, headers, HttpStatus.NOT_FOUND, request);
        } else if (e instanceof ReservationNotFoundException) {
            return handleReservationNotFoundException
                    ((ReservationNotFoundException) e, headers, HttpStatus.NOT_FOUND, request);
        } else if (e instanceof DateTimeParseException) {
            return handleDateTimeParseException
                    ((DateTimeParseException) e, headers, HttpStatus.BAD_REQUEST, request);
        } else if (e instanceof InvalidOrderException) {
            return handleInvalidOrderException
                    ((InvalidOrderException) e, headers, HttpStatus.BAD_REQUEST, request);
        } else if (e instanceof RoomAlreadyBookException) {
            return handleRoomAlreadyBookException
                    ((RoomAlreadyBookException) e, headers, HttpStatus.BAD_REQUEST, request);
        } else if (e instanceof RoomAlreadyExistException) {
            return handleRoomAlreadyExistException
                    ((RoomAlreadyExistException) e, headers, HttpStatus.BAD_REQUEST, request);
       /* } else if (e instanceof MethodArgumentNotValidException) {
            return handleMethodArgumentNotValidException
                    ((MethodArgumentNotValidException) e, headers, HttpStatus.BAD_REQUEST, request);*/
        } else {
            return handleExceptionInternal
                    (e, null, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
        }
    }

    private ResponseEntity<Error> handleDateTimeParseException(DateTimeParseException e, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        return handleExceptionInternal(e, new Error(e.getMessage()), headers, status, request);
    }
    private ResponseEntity<Error> handleRoomAlreadyExistException(RoomAlreadyExistException e, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        return handleExceptionInternal(e, new Error(e.getMessage()), headers, status, request);
    }
    private ResponseEntity<Error> handleRoomAlreadyBookException(RoomAlreadyBookException e, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        return handleExceptionInternal(e, new Error(e.getMessage()), headers, status, request);
    }

    private ResponseEntity<Error> handleInvalidOrderException(InvalidOrderException e, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        return handleExceptionInternal(e, new Error(e.getMessage()), headers, status, request);
    }

    private ResponseEntity<Error> handleGuestNotFoundException(GuestNotFoundException e, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        return handleExceptionInternal(e, new Error(e.getMessage()), headers, status, request);
    }

    private ResponseEntity<Error> handleAttendanceNotFoundException(AttendanceNotFoundException e, HttpHeaders headers,
                                                                    HttpStatus status, WebRequest request) {
        return handleExceptionInternal(e, new Error(e.getMessage()), headers, status, request);
    }

    private ResponseEntity<Error> handleRoomNotFoundException(RoomNotFoundException e, HttpHeaders headers,
                                                              HttpStatus status, WebRequest request) {
        return handleExceptionInternal(e, new Error(e.getMessage()), headers, status, request);
    }

    private ResponseEntity<Error> handleReservationNotFoundException(ReservationNotFoundException e, HttpHeaders headers,
                                                                     HttpStatus status, WebRequest request) {
        return handleExceptionInternal(e, new Error(e.getMessage()), headers, status, request);
    }

    private ResponseEntity<Error> handleFeatureNotFoundException(FeatureNotFouundException e, HttpHeaders headers,
                                                                 HttpStatus status, WebRequest request) {
        return handleExceptionInternal(e, new Error(e.getMessage()), headers, status, request);
    }
    //protected ResponseEntity<Error> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
    // HttpHeaders headers, HttpStatus status, WebRequest request) {
    //    return handleExceptionInternal(e, new Error(getMessageFromMANVE(e)), headers, status, request);
    //}

    private ResponseEntity<Error> handleExceptionInternal(Exception e, Error body, HttpHeaders headers,
                                                          HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, e, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }

}
