package by.nc.tarazenko.controller;

import by.nc.tarazenko.dtos.FeatureDTO;
import by.nc.tarazenko.service.FeatureService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("features")
public class FeatureController {
    private Logger logger = Logger.getLogger(RoomController.class);

    private final FeatureService featureService;

    @Autowired
    public FeatureController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @GetMapping
    public ResponseEntity<List<FeatureDTO>> getAll() {
        return ResponseEntity.ok(featureService.getAll());
    }

    @PostMapping
    public ResponseEntity<FeatureDTO> create(@RequestBody FeatureDTO featureDTO) {
        logger.debug("Seve feature = " + featureDTO);
        return ResponseEntity.ok(featureService.create(featureDTO));
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<FeatureDTO> update(@RequestBody FeatureDTO featureDTO, @PathVariable int id) {
        return ResponseEntity.ok(featureService.update(featureDTO));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FeatureDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(featureService.getById(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable int id) {
        featureService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
