package by.nc.tarazenko.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorDTO{
    HttpStatus httpStatus;
    String exceptionName;
    String message;
}
