package by.nc.tarazenko.controller;

import by.nc.tarazenko.dtos.AttendanceDTO;
import by.nc.tarazenko.service.AttendanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("attendances")
@Slf4j
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping
    public ResponseEntity<List<AttendanceDTO>> getAttendances() {
        log.debug("Get all attendances.");
        List<AttendanceDTO> attendanceDTOS = attendanceService.getAll();
        log.debug("Attendances - {}", attendanceDTOS);
        return ResponseEntity.ok(attendanceDTOS);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AttendanceDTO> getAttendance(@PathVariable int id) {
        log.debug("Get attendance in: id - {}", id);
        AttendanceDTO attendanceDTO = attendanceService.getById(id);
        log.debug("Get attendance out: result - {}", attendanceDTO);
        return ResponseEntity.ok(attendanceDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AttendanceDTO> update(@RequestBody AttendanceDTO attendance, @PathVariable int id) {
        attendance.setId(id);
        log.debug("Update attendance in: attendance - {}", attendance);
        AttendanceDTO attendanceResult = attendanceService.update(attendance);
        log.debug("Update attendance out: result attendance - {}", attendanceResult);
        return ResponseEntity.ok(attendanceResult);
    }

    @PostMapping
    public ResponseEntity<AttendanceDTO> create(@RequestBody AttendanceDTO attendance) {
        log.debug("Create attendance in: attendance - {}", attendance);
        AttendanceDTO attendanceResult = attendanceService.create(attendance);
        log.debug("Create attendance out: result attendance - {}", attendanceResult);
        return ResponseEntity.ok(attendanceResult);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable int id) {
        log.debug("Delete attendance in: id - {}", id);
        attendanceService.deleteById(id);
        log.debug("Delete attendance id - {}", id);
        return ResponseEntity.ok().build();
    }
}
