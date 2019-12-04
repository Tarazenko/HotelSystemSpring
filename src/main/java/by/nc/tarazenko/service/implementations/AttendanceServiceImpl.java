package by.nc.tarazenko.service.implementations;

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

    @Override
    public AttendanceDTO getById(int id) {
        return toDTO(attendanceRepository.getOne(id));
    }

    private AttendanceDTO toDTO(Attendance attendance) {
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setId(attendance.getId());
        attendanceDTO.setCost(attendance.getCost());
        attendanceDTO.setName(attendance.getName());
        return attendanceDTO;
    }


    @Override
    public List<AttendanceDTO> getAll() {
        List<Attendance> attendances = attendanceRepository.findAll();
        List<AttendanceDTO> attendanceDTOs = new ArrayList<>();
        for(Attendance attendance: attendances){
            attendanceDTOs.add(toDTO(attendance));
        }
        return attendanceDTOs;
    }

    @Override
    public void create(AttendanceDTO entity) {
    }

    @Override
    public boolean update(AttendanceDTO attendanceDTO) {
        Attendance newAttendance = fromDTO(attendanceDTO);
        boolean ok = true;
        Attendance attendance = attendanceRepository.getOne(newAttendance.getId());
        if(attendance != null){
            attendance.setCost(newAttendance.getCost());
            attendance.setName(newAttendance.getName());
            logger.debug(attendance);
            attendanceRepository.saveAndFlush(attendance);
        }else{
            ok = false;
        }
        return ok;
    }

    private Attendance fromDTO(AttendanceDTO attendanceDTO) {
        Attendance attendance = new Attendance();
        attendance.setCost(attendanceDTO.getCost());
        attendance.setId(attendanceDTO.getId());
        attendance.setName(attendanceDTO.getName());
        return attendance;
    }

    @Override
    public boolean deleteById(int entityId) {
        return false;
    }
}
