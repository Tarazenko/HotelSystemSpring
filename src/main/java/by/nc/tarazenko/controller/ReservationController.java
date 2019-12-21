package by.nc.tarazenko.controller;

import by.nc.tarazenko.dtos.ReservationDTO;
import by.nc.tarazenko.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationDTO> getById(@PathVariable int id) {
        log.debug("Get reservation in: id - {}", id);
        ReservationDTO reservationDTO = reservationService.getById(id);
        log.debug("Get reservation out: result - {}", reservationDTO);
        return ResponseEntity.ok(reservationDTO);
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAll() {
        log.debug("Get all reservations.");
        List<ReservationDTO> reservationDTOs = reservationService.getAll();
        log.debug("Reservations - {}", reservationDTOs);
        return ResponseEntity.ok(reservationDTOs);
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> create(@RequestBody ReservationDTO reservationDTO) {
        log.debug("Create reservation in: reservation - {}", reservationDTO);
        ReservationDTO reservationResult = reservationService.create(reservationDTO);
        log.debug("Create reservation out: result reservation - {}", reservationResult);
        return ResponseEntity.ok(reservationResult);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ReservationDTO> update(@RequestBody ReservationDTO reservationDTO,
                                                 @PathVariable int id) {
        reservationDTO.setId(id);
        log.debug("Update reservation in: reservation - {}", reservationDTO);
        ReservationDTO reservationResult = reservationService.update(reservationDTO);
        log.debug("Update reservation out: result reservation - {}", reservationResult);
        return ResponseEntity.ok(reservationResult);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable int id) {
        log.debug("Delete reservation in: id - {}", id);
        reservationService.deleteById(id);
        log.debug("Delete reservation id - {}", id);
        return ResponseEntity.ok().build();
    }
}
