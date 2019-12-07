package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.convector.AttendanceConvector;
import by.nc.tarazenko.dtos.AttendanceDTO;
import by.nc.tarazenko.entity.Attendance;
import by.nc.tarazenko.repository.AttendanceRepository;
import by.nc.tarazenko.service.AttendanceService;
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
        return attendanceConvector.toDTO(attendanceRepository.getOne(id));
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
    public void create(AttendanceDTO attendanceDTO) {
        attendanceRepository.saveAndFlush(attendanceConvector.fromDTO(attendanceDTO));
    }

    @Override
    public boolean update(AttendanceDTO attendanceDTO) {
        Attendance newAttendance = attendanceConvector.fromDTO(attendanceDTO);
        boolean ok = true;
        Attendance attendance = attendanceRepository.getOne(newAttendance.getId());
        if (attendance != null) {
            attendance.setCost(newAttendance.getCost());
            attendance.setName(newAttendance.getName());
            logger.debug(attendance);
            attendanceRepository.saveAndFlush(attendance);
        } else {
            ok = false;
        }
        return ok;
    }

    @Override
    public boolean deleteById(int attendanceId) {
        boolean ok = true;
        if (attendanceRepository.findById(attendanceId).isPresent()) {
            attendanceRepository.deleteById(attendanceId);
            logger.debug("Found and deleted with id = " + attendanceId);
        } else {
            logger.debug("Attendance not found.");
            ok = false;
        }
        return ok;
    }
}
