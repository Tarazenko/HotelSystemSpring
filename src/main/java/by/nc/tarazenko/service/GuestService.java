package by.nc.tarazenko.service;

import by.nc.tarazenko.dtos.GuestDTO;
import by.nc.tarazenko.entity.Attendance;
import by.nc.tarazenko.entity.AttendancesGuestsConnect;
import by.nc.tarazenko.entity.Guest;

import java.util.List;
import java.util.Set;

public interface GuestService extends Service<GuestDTO> {
    List<Attendance> getAttendances(int guestId);
    int addAttendance(int guestId, int attendanceId);
}
