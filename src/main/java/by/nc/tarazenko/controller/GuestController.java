package by.nc.tarazenko.controller;

import by.nc.tarazenko.dtos.GuestDTO;
import by.nc.tarazenko.entity.Attendance;
import by.nc.tarazenko.service.GuestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("guests")
@Slf4j
public class GuestController {

    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public ResponseEntity<List<GuestDTO>> getAll() {
        log.debug("Get all guests");
        List<GuestDTO> guests = guestService.getAll();
        log.debug("Guests - {}", guests);
        return ResponseEntity.ok(guests);

    }

    @PostMapping
    public ResponseEntity<GuestDTO> create(@RequestBody GuestDTO guest) {
        log.debug("Create guest in: guest - {}", guest);
        GuestDTO guestResult = guestService.create(guest);
        log.debug("Create guest out: result guest - {}", guestResult);
        return ResponseEntity.ok(guestResult);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GuestDTO> update(@RequestBody GuestDTO guest, @PathVariable int id) {
        guest.setId(id);
        log.debug("Update guest in: guest - {}", guest);
        GuestDTO guestResult = guestService.update(guest);
        log.debug("Update guest out: guest - {}", guestResult);
        return ResponseEntity.ok(guestResult);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GuestDTO> getById(@PathVariable int id) {
        log.debug("Get guest in: id - {}", id);
        GuestDTO guestDTO = guestService.getById(id);
        log.debug("Get guest out: guest - {}", guestDTO);
        return ResponseEntity.ok(guestDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable int id) {
        log.debug("Delete guest in: id - {}", id);
        guestService.deleteById(id);
        log.debug("Delete guest id - {}", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/attendances")
    public ResponseEntity<List<Attendance>> getAttendances(@PathVariable int id) {
        log.debug("Get guest attendances in: guestId - {}", id);
        List<Attendance> attendances = guestService.getAttendances(id);
        log.debug("Get guest attendances out: attendances - {}", attendances);
        return ResponseEntity.ok(attendances);

    }

    @PostMapping(value = "/{guestId}/attendances/{attendanceId}")
    public ResponseEntity<GuestDTO> setAttendance(@PathVariable int guestId,
                                                  @PathVariable int attendanceId) {
        log.debug("Add attendances to guest in: guestId - {}, attendanceId - {}", guestId, attendanceId);
        GuestDTO guestDTO = guestService.addAttendance(guestId, attendanceId);
        log.debug("Add attendances to guest out: result - {}", guestDTO);
        return ResponseEntity.ok(guestDTO);
    }
}
