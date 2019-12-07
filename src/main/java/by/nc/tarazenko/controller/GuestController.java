package by.nc.tarazenko.controller;

import by.nc.tarazenko.dtos.GuestDTO;
import by.nc.tarazenko.entity.Attendance;
import by.nc.tarazenko.repository.UserRepository;
import by.nc.tarazenko.service.GuestService;

import by.nc.tarazenko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.apache.log4j.Logger;

@RestController
@RequestMapping("guests")
public class GuestController {
    private Logger logger = Logger.getLogger(GuestController.class);

    @Autowired
    private GuestService guestService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<GuestDTO>> getAll() {
        try {
            //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            //String name =  auth.getName();
            //System.out.println(userRepository.findByUserName(name).get().getAuthorities());
            return ResponseEntity.ok(guestService.getAll());
        } catch (Exception ex) {
            logger.warn(ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody GuestDTO guest) {
        try {
            logger.info("Save guest");
            logger.debug("Seve guest = " + guest);
            guestService.create(guest);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            logger.warn(ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody GuestDTO guest, @PathVariable int id) {
        try {
            guest.setId(id);
            if (guestService.update(guest)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            logger.warn(ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GuestDTO> getById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(guestService.getById(id));
            //todo don't right check exception
        } catch (Exception e) {
            logger.info("No guest with id " + id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable int id) {
        if (guestService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            logger.info("No guest with id " + id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{id}/attendances")
    public ResponseEntity<List<Attendance>> getAttendances(@PathVariable int id) {
        try {
            return ResponseEntity.ok(guestService.getAttendances(id));
        } catch (Exception ex) {
            logger.warn(ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/{guestId}/attendances/{attendanceId}")
    public ResponseEntity<GuestDTO> setAttendance(@PathVariable int guestId,
                                                  @PathVariable int attendanceId) {
        try {
            int checker = guestService.addAttendance(guestId, attendanceId);
            if (checker == -1) {
                //todo information message
                return ResponseEntity.notFound().build();
            } else if (checker == 0) {
                return ResponseEntity.notFound().build();
            } else{
                return ResponseEntity.ok().build();
            }
        } catch (Exception ex) {
            logger.warn(ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
