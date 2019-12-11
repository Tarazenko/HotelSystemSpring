package by.nc.tarazenko.controller;
import by.nc.tarazenko.dtos.ReservationDTO;
import by.nc.tarazenko.entity.Reservation;
import by.nc.tarazenko.repository.ReservationRepository;
import by.nc.tarazenko.service.ReservationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reservations")
public class ReservationController {
    private Logger logger = Logger.getLogger(ReservationController.class);

    @Autowired
    private ReservationService reservationService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(reservationService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAll() {
        return ResponseEntity.ok(reservationService.getAll());
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> create(@RequestBody ReservationDTO reservationDTO) {
        return ResponseEntity.ok(reservationService.create(reservationDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ReservationDTO> update(@RequestBody ReservationDTO reservationDTO, @PathVariable int id) {
        reservationDTO.setId(id);
        return ResponseEntity.ok(reservationService.update(reservationDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable int id) {
        reservationService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
