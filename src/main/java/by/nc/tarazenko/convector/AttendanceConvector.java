package by.nc.tarazenko.convector;

import by.nc.tarazenko.dtos.AttendanceDTO;
import by.nc.tarazenko.entity.Attendance;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AttendanceConvector implements Convector<AttendanceDTO, Attendance> {

    @Override
    public AttendanceDTO toDTO(Attendance attendance) {
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setId(attendance.getId());
        attendanceDTO.setCost(attendance.getCost());
        attendanceDTO.setName(attendance.getName());
        return attendanceDTO;
    }

    @Override
    public Attendance fromDTO(AttendanceDTO attendanceDTO) {
        Attendance attendance = new Attendance();
        attendance.setCost(attendanceDTO.getCost());
        attendance.setId(attendanceDTO.getId());
        attendance.setName(attendanceDTO.getName());
        return attendance;
    }
}
