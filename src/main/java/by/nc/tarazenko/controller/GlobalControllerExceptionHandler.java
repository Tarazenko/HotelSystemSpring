package by.nc.tarazenko.controller;

import by.nc.tarazenko.dtos.ErrorDTO;
import by.nc.tarazenko.service.exceptions.*;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private Logger logger = Logger.getLogger(GlobalControllerExceptionHandler.class);


    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
      //  log.error(ex.getClass().getName(), ex);
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.NOT_FOUND, ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }
    @ExceptionHandler(EntityExistException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityExistException ex) {
      //  log.error(ex.getClass().getName(), ex);
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.CONFLICT, ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }
    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleEntityNotFound(BadRequestException ex) {
      //  log.error(ex.getClass().getName(), ex);
        logger.debug("not Found");
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request){
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        logger.debug(errors);
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors.toString());
        return handleExceptionInternal(ex, errorDTO, headers, errorDTO.getHttpStatus(), request);
    }
}
