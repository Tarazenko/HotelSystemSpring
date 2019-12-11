package by.nc.tarazenko.controller;

import by.nc.tarazenko.dtos.GuestDTO;
import by.nc.tarazenko.dtos.RoomDTO;
import by.nc.tarazenko.service.RoomService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("rooms")
public class RoomController {

    private Logger logger = Logger.getLogger(RoomController.class);

    @Autowired
    RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAll(@RequestParam (required = false)String checkin,
                                                @RequestParam (required = false)String checkout) {
        LocalDate checkinDate = (checkin == null)? null: LocalDate.parse(checkin);
        LocalDate checkoutDate = (checkout == null)? null:LocalDate.parse(checkout);
        if(checkinDate == null && checkoutDate == null)
            return ResponseEntity.ok(roomService.getAll());
        else
            return ResponseEntity.ok(roomService.getFree(checkinDate,checkoutDate));
    }

    @PostMapping
    public ResponseEntity<RoomDTO> create(@RequestBody RoomDTO roomDTO) {
        logger.debug("Seve room = " + roomDTO);
        return ResponseEntity.ok(roomService.create(roomDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RoomDTO> update(@RequestBody RoomDTO roomDTO, @PathVariable int id) {
       roomDTO.setId(id);
       return ResponseEntity.ok(roomService.update(roomDTO));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RoomDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(roomService.getById(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable int id) {
       roomService.deleteById(id);
       return ResponseEntity.ok().build();
    }
}

