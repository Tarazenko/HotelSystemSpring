package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.convector.FeatureConvector;
import by.nc.tarazenko.dtos.FeatureDTO;
import by.nc.tarazenko.entity.Feature;
import by.nc.tarazenko.repository.FeatureRepository;
import by.nc.tarazenko.service.FeatureService;
import by.nc.tarazenko.service.exceptions.FeatureAlreadyExistException;
import by.nc.tarazenko.service.exceptions.FeatureNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FeatureServiceImpl implements FeatureService {

    private final FeatureRepository featureRepository;

    private final FeatureConvector featureConvector = new FeatureConvector();

    @Autowired
    public FeatureServiceImpl(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @Override
    public FeatureDTO getById(int id) {
        log.debug("Getting feature(id - {})", id);
        Feature feature = featureRepository.findById(id).orElseThrow(() ->
                new FeatureNotFoundException(String.format("There is no feature with id - %d", id)));
        log.debug("Returning feature {}", feature);
        return featureConvector.toDTO(feature);
    }

    @Override
    public List<FeatureDTO> getAll() {
        log.debug("Getting all features");
        List<Feature> features = featureRepository.findAll();
        List<FeatureDTO> featureDTOs = new ArrayList<>();
        for (Feature feature : features) {
            featureDTOs.add(featureConvector.toDTO(feature));
        }
        log.debug("Returning - {}", features);
        return featureDTOs;
    }

    @Override
    public FeatureDTO create(FeatureDTO featureDTO) {
        log.debug("Creating {}", featureDTO);
        Feature feature = featureConvector.fromDTO(featureDTO);
        if (featureRepository.getFeatureByName(feature.getName()) != null) {
            throw new FeatureAlreadyExistException(String.format("Feature with name - %s - already exist.",
                    feature.getName()));
        }
        feature = featureRepository.saveAndFlush(feature);
        log.debug("Created {}", feature);
        return featureConvector.toDTO(feature);
    }

    @Override
    public FeatureDTO update(FeatureDTO featureDTO) {
        log.debug("Updating {}", featureDTO);
        Feature feature = featureConvector.fromDTO(featureDTO);
        int id = feature.getId();
        featureRepository.findById(feature.getId()).orElseThrow(() ->
                new FeatureNotFoundException(String.format("There is no feature with id - %d.", id)));
        feature = featureRepository.saveAndFlush(feature);
        log.debug("Update {}", feature);
        return featureConvector.toDTO(feature);
    }

    @Override
    public void deleteById(int id) {
        log.debug("Deleting feature id - ", id);
        featureRepository.findById(id).orElseThrow(() ->
                new FeatureNotFoundException(String.format("There is no feature with id - %d.", id)));
        featureRepository.deleteById(id);
        log.debug("Deleted feature id - {}", id);
    }
}
