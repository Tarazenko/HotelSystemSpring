package by.nc.tarazenko.controller;

import by.nc.tarazenko.dtos.ErrorDTO;
import by.nc.tarazenko.service.exceptions.BadRequestException;
import by.nc.tarazenko.service.exceptions.EntityExistException;
import by.nc.tarazenko.service.exceptions.EntityNotFoundException;
import by.nc.tarazenko.service.exceptions.InvalidOrderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        log.warn("Not found exception stack: {} ", Arrays.toString(ex.getStackTrace()));
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.NOT_FOUND, ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }

    @ExceptionHandler(EntityExistException.class)
    protected ResponseEntity<Object> handleEntityExist(EntityExistException ex) {
        log.warn("Entity already exist exception stack: {} ", Arrays.toString(ex.getStackTrace()));
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.CONFLICT, ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequest(BadRequestException ex) {
        log.warn("Bad request exception stack: {} ", Arrays.toString(ex.getStackTrace()));
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }

    @ExceptionHandler(DateTimeParseException.class)
    protected ResponseEntity<Object> handleDateTimeParseException(DateTimeParseException ex) {
        log.warn("Date time parse exception stack: {} ", Arrays.toString(ex.getStackTrace()));
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }

    @ExceptionHandler(InvalidOrderException.class)
    protected ResponseEntity<Object> handleInvalidOrderException(InvalidOrderException ex) {
        log.warn("Invalid order exception stack: {} ", Arrays.toString(ex.getStackTrace()));
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }

    @ExceptionHandler(SQLException.class)
    protected ResponseEntity<Object> handleSqlException(SQLException ex) {
        log.warn("Invalid order exception stack: {} ", Arrays.toString(ex.getStackTrace()));
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(
            final DataIntegrityViolationException ex, final WebRequest request) {
        log.warn("Sql exception stack: {} ", Arrays.toString(ex.getStackTrace()));
        List<String> errors = new ArrayList<>();
        errors.add("Entity dependency broken.");
        ErrorDTO errorDTO =
                new ErrorDTO(HttpStatus.CONFLICT, ex.getLocalizedMessage(), errors.toString());
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleAll(Exception ex) {
        log.warn("Server exception stack: {} ", Arrays.toString(ex.getStackTrace()));
        ErrorDTO errorDto =
                new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
        return new ResponseEntity<>(errorDto, new HttpHeaders(), errorDto.getHttpStatus());
    }
}
