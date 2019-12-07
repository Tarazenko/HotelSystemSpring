package by.nc.tarazenko.controller;

import by.nc.tarazenko.dtos.FeatureDTO;
import by.nc.tarazenko.service.FeatureService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("features")
public class FeatureController {
    private Logger logger = Logger.getLogger(RoomController.class);

    @Autowired
    FeatureService featureService;

    @GetMapping
    public ResponseEntity<List<FeatureDTO>> getAll() {
        return ResponseEntity.ok(featureService.getAll());
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody FeatureDTO featureDTO) {
        logger.debug("Seve feature = " + featureDTO);
        featureService.create(featureDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody FeatureDTO featureDTO, @PathVariable int id) {
        featureDTO.setId(id);
        if (featureService.update(featureDTO))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FeatureDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(featureService.getById(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable int id) {
        if (featureService.deleteById(id))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
