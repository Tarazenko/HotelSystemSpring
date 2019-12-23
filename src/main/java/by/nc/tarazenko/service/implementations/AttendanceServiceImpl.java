package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.convector.AttendanceConvector;
import by.nc.tarazenko.dtos.AttendanceDTO;
import by.nc.tarazenko.entity.Attendance;
import by.nc.tarazenko.repository.AttendanceRepository;
import by.nc.tarazenko.service.AttendanceService;
import by.nc.tarazenko.service.exceptions.AttendanceAlreadyExistException;
import by.nc.tarazenko.service.exceptions.AttendanceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final AttendanceConvector attendanceConvector = new AttendanceConvector();

    @Autowired
    public AttendanceServiceImpl(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public AttendanceDTO getById(int id) {
        log.debug("Getting attendance(id - {})", id);
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(() ->
                new AttendanceNotFoundException(String.format("Attendance (id - {%s}) not found", id)));
        log.debug("Returning attendance {}", attendance);
        return attendanceConvector.toDTO(attendance);
    }

    @Override
    public List<AttendanceDTO> getAll() {
        log.debug("Getting all attendances");
        List<Attendance> attendances = attendanceRepository.findAll();
        List<AttendanceDTO> attendanceDTOs = new ArrayList<>();
        for (Attendance attendance : attendances) {
            attendanceDTOs.add(attendanceConvector.toDTO(attendance));
        }
        log.debug("Returning - {}", attendances);
        return attendanceDTOs;
    }

    @Override
    public AttendanceDTO create(AttendanceDTO attendanceDTO) {
        log.debug("Creating {}", attendanceDTO);
        Attendance attendance = attendanceConvector.fromDTO(attendanceDTO);
        if (attendanceRepository.existsByName(attendance.getName())) {
            throw new AttendanceAlreadyExistException(String.format("Attendance with name %s exist",
                    attendance.getName()));
        }
        attendance = attendanceRepository.saveAndFlush(attendance);
        log.debug("Created {}", attendance);
        return attendanceConvector.toDTO(attendance);
    }

    @Override
    public AttendanceDTO update(AttendanceDTO attendanceDTO) {
        log.debug("Updating {}", attendanceDTO);
        Attendance attendance = attendanceConvector.fromDTO(attendanceDTO);
        int id = attendance.getId();
        attendanceRepository.findById(attendance.getId()).orElseThrow(() ->
                new AttendanceNotFoundException(String.format("There is no attendance(id - %d)", id)));
        attendance = attendanceRepository.saveAndFlush(attendance);
        log.debug("Update {}", attendance);
        return attendanceConvector.toDTO(attendance);
    }

    @Override
    public void deleteById(int attendanceId) {
        log.debug("Deleting attendance id - {}", attendanceId);
        attendanceRepository.findById(attendanceId).orElseThrow(() ->
                new AttendanceNotFoundException(String.format("There is no attendance (id - %d)",
                        attendanceId)));
        attendanceRepository.deleteById(attendanceId);
        log.debug("Deleted attendance id - {}", attendanceId);
    }
}
