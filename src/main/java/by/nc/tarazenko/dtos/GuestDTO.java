package by.nc.tarazenko.dtos;

import by.nc.tarazenko.entity.Attendance;
import lombok.Data;

import java.util.List;

@Data
public class GuestDTO {
    private int id;
    private String phoneNumber;
    private String firstName;
    private String secondName;
    private String thirdName;
    private String passportNumber;
    private List<Attendance> attendances;
    private double bill;
}
