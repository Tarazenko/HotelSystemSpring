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
        try{
            return ResponseEntity.ok(attendanceService.getAll());
        } catch (Exception ex) {
            logger.warn(ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AttendanceDTO> getAttendance(@PathVariable int id){
        try{
            AttendanceDTO attendance = attendanceService.getById(id);
            if(attendance == null){
                return ResponseEntity.notFound().build();
            }else{
                return ResponseEntity.ok(attendance);
            }
        }catch (Exception ex){
            logger.warn(ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AttendanceDTO> update(@RequestBody AttendanceDTO attendance, @PathVariable int id) {
        try {
            attendance.setId(id);
            logger.debug(attendance);
            if (attendanceService.update(attendance)) {
                return ResponseEntity.ok(attendanceService.getById(id));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            logger.warn(ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody AttendanceDTO attendance) {
        try {
            logger.debug("Seve attendance = " + attendance);
            attendanceService.create(attendance);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            logger.warn(ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable int id) {
        if (attendanceService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            logger.info("No guest with id " + id);
            return ResponseEntity.notFound().build();
        }
    }
}
