package by.nc.tarazenko.controller;

import by.nc.tarazenko.dtos.AttendanceDTO;
import by.nc.tarazenko.service.AttendanceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("attendances")
public class AttendanceController {
    private Logger logger = Logger.getLogger(GuestController.class);

    @Autowired
    AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<List<AttendanceDTO>> getAttendances(){
        return ResponseEntity.ok(attendanceService.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AttendanceDTO> getAttendance(@PathVariable int id){
        return ResponseEntity.ok(attendanceService.getById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AttendanceDTO> update(@RequestBody AttendanceDTO attendance, @PathVariable int id) {
        attendance.setId(id);
        return  ResponseEntity.ok(attendanceService.update(attendance));
    }

    @PostMapping
    public ResponseEntity<AttendanceDTO> create(@RequestBody AttendanceDTO attendance) {
        return ResponseEntity.ok(attendanceService.create(attendance));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable int id) {
        attendanceService.deleteById(id);
        return  ResponseEntity.ok().build();
    }
}
