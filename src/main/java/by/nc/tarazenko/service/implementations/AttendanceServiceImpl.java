package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.convector.AttendanceConvector;
import by.nc.tarazenko.dtos.AttendanceDTO;
import by.nc.tarazenko.entity.Attendance;
import by.nc.tarazenko.repository.AttendanceRepository;
import by.nc.tarazenko.service.AttendanceService;
import by.nc.tarazenko.service.exceptions.AttendanceNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    final static Logger logger = Logger.getLogger(GuestServiceImpl.class);

    @Autowired
    AttendanceRepository attendanceRepository;

    private AttendanceConvector attendanceConvector = new AttendanceConvector();

    @Override
    public AttendanceDTO getById(int id) {
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(()->
                new AttendanceNotFoundException("There is no such attendance."));
        return attendanceConvector.toDTO(attendance);
    }

    @Override
    public List<AttendanceDTO> getAll() {
        List<Attendance> attendances = attendanceRepository.findAll();
        List<AttendanceDTO> attendanceDTOs = new ArrayList<>();
        for (Attendance attendance : attendances) {
            attendanceDTOs.add(attendanceConvector.toDTO(attendance));
        }
        return attendanceDTOs;
    }

    @Override
    public AttendanceDTO create(AttendanceDTO attendanceDTO) {
        Attendance attendance = attendanceConvector.fromDTO(attendanceDTO);
        attendance = attendanceRepository.saveAndFlush(attendance);
        return attendanceConvector.toDTO(attendance);
    }

    @Override
    public AttendanceDTO update(AttendanceDTO attendanceDTO) {
        Attendance attendance = attendanceConvector.fromDTO(attendanceDTO);
        attendance = attendanceRepository.findById(attendance.getId()).orElseThrow(()->
                new AttendanceNotFoundException("There is no such attendance."));
        attendance = attendanceRepository.saveAndFlush(attendance);
        return attendanceConvector.toDTO(attendance);
    }

    @Override
    public void deleteById(int attendanceId) {
       Attendance attendance = attendanceRepository.findById(attendanceId).orElseThrow(()->
                new AttendanceNotFoundException("There is no such attendance."));
       attendanceRepository.deleteById(attendanceId);
    }
}
