package by.nc.tarazenko.controller;

import by.nc.tarazenko.dtos.RoomDTO;
import by.nc.tarazenko.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAll(@RequestParam(required = false) String checkin,
                                                @RequestParam(required = false) String checkout) {
        log.debug("Get all rooms, dates: checkin - {}, checkout - {}", checkin, checkout);
        LocalDate checkinDate = (checkin == null) ? null : LocalDate.parse(checkin);
        LocalDate checkoutDate = (checkout == null) ? null : LocalDate.parse(checkout);
        if (checkinDate == null && checkoutDate == null) {
            List<RoomDTO> roomDTOS = roomService.getAll();
            log.debug("Rooms - {}", roomDTOS);
            return ResponseEntity.ok(roomDTOS);
        } else {
            List<RoomDTO> roomDTOS = roomService.getFree(checkinDate, checkoutDate);
            log.debug("Free rooms - {}", roomDTOS);
            return ResponseEntity.ok(roomDTOS);
        }
    }

    @PostMapping
    public ResponseEntity<RoomDTO> create(@Valid @RequestBody RoomDTO roomDTO) {
        log.debug("Create room in: room - {}", roomDTO);
        RoomDTO roomResult = roomService.create(roomDTO);
        log.debug("Create room out: result room - {}", roomResult);
        return ResponseEntity.ok(roomResult);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RoomDTO> update(@Valid @RequestBody RoomDTO roomDTO, @PathVariable int id) {
        roomDTO.setId(id);
        log.debug("Update room in: room - {}", roomDTO);
        RoomDTO roomResult = roomService.update(roomDTO);
        log.debug("Update room out: room user - {}", roomResult);
        return ResponseEntity.ok(roomResult);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RoomDTO> getById(@PathVariable int id) {
        log.debug("Get room in: id - {}", id);
        RoomDTO roomDTO = roomService.getById(id);
        log.debug("Get room out: result - {}", roomDTO);
        return ResponseEntity.ok(roomDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable int id) {
        log.debug("Delete room in: id - {}", id);
        roomService.deleteById(id);
        log.debug("Delete room id - {}", id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{roomId}/features/{featureId}")
    public ResponseEntity<RoomDTO> addFeature(@PathVariable int roomId,
                                              @PathVariable int featureId) {
        log.debug("Add feature to room in: roomId - {}, featureId - {}", roomId, featureId);
        RoomDTO roomDTO = roomService.addFeature(roomId, featureId);
        log.debug("Add feature to room out: roomDTO - {}", roomDTO);
        return ResponseEntity.ok(roomDTO);
    }
}

