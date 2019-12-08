package by.nc.tarazenko.service;

import by.nc.tarazenko.dtos.GuestDTO;
import by.nc.tarazenko.entity.Attendance;

import java.util.List;

public interface GuestService extends Service<GuestDTO> {
    List<Attendance> getAttendances(int guestId);
    GuestDTO addAttendance(int guestId, int attendanceId);
}
