package by.nc.tarazenko.controller;

import by.nc.tarazenko.dtos.FeatureDTO;
import by.nc.tarazenko.service.FeatureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("features")
public class FeatureController {

    private final FeatureService featureService;

    @Autowired
    public FeatureController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @GetMapping
    public ResponseEntity<List<FeatureDTO>> getAll() {
        log.debug("Getting all features");
        List<FeatureDTO> featureDTOS = featureService.getAll();
        log.debug("Features - {}", featureDTOS);
        return ResponseEntity.ok(featureDTOS);
    }

    @PostMapping
    public ResponseEntity<FeatureDTO> create(@RequestBody FeatureDTO feature) {
        log.debug("Create feature in: feature - {}", feature);
        FeatureDTO featureDTO = featureService.create(feature);
        log.debug("Create feature out: created feature - {}", featureDTO);
        return ResponseEntity.ok(featureDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<FeatureDTO> update(@RequestBody FeatureDTO feature, @PathVariable int id) {
        feature.setId(id);
        log.debug("Update feature in: feature - {}", feature);
        FeatureDTO featureDTO = featureService.update(feature);
        log.debug("Update feature out: result feature - {}", featureDTO);
        return ResponseEntity.ok(featureDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FeatureDTO> getById(@PathVariable int id) {
        log.debug("Get feature in: id - {}", id);
        FeatureDTO featureDTO = featureService.getById(id);
        log.debug("Get feature out: result - {}", featureDTO);
        return ResponseEntity.ok(featureDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteFeture(@PathVariable int id) {
        log.debug("Delete feature in: id - {}", id);
        featureService.deleteById(id);
        log.debug("Delete feature with id - {}", id);
        return ResponseEntity.ok().build();
    }
}
