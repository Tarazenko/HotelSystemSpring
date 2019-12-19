package by.nc.tarazenko.controller;

import by.nc.tarazenko.dtos.GuestDTO;
import by.nc.tarazenko.entity.Attendance;
import by.nc.tarazenko.service.GuestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.apache.log4j.Logger;

@RestController
@RequestMapping("guests")
public class GuestController {
    private Logger logger = Logger.getLogger(GuestController.class);

    @Autowired
    private GuestService guestService;

    @GetMapping
    public ResponseEntity<List<GuestDTO>> getAll() {
        return ResponseEntity.ok(guestService.getAll());

    }

    @PostMapping
    public ResponseEntity<GuestDTO> create(@RequestBody GuestDTO guest) {
        return ResponseEntity.ok(guestService.create(guest));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GuestDTO> update(@RequestBody GuestDTO guest, @PathVariable int id) {
        guest.setId(id);
        return ResponseEntity.ok(guestService.update(guest));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GuestDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(guestService.getById(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable int id) {
        guestService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/attendances")
    public ResponseEntity<List<Attendance>> getAttendances(@PathVariable int id) {
        return ResponseEntity.ok(guestService.getAttendances(id));

    }

    @PostMapping(value = "/{guestId}/attendances/{attendanceId}")
    public ResponseEntity<GuestDTO> setAttendance(@PathVariable int guestId,
                                                  @PathVariable int attendanceId) {
        return ResponseEntity.ok(guestService.addAttendance(guestId, attendanceId));
    }
}
