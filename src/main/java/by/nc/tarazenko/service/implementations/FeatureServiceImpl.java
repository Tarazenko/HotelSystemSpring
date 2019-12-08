package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.convector.FeatureConvector;
import by.nc.tarazenko.dtos.FeatureDTO;
import by.nc.tarazenko.entity.Feature;
import by.nc.tarazenko.repository.FeatureRepository;
import by.nc.tarazenko.service.FeatureService;
import by.nc.tarazenko.service.RoomService;
import by.nc.tarazenko.service.exceptions.FeatureNotFouundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeatureServiceImpl implements FeatureService {

    private Logger logger = Logger.getLogger(RoomService.class);

    @Autowired
    FeatureRepository featureRepository;

    private FeatureConvector featureConvector = new FeatureConvector();

    @Override
    public FeatureDTO getById(int id) {
        Feature feature = featureRepository.findById(id).orElseThrow(()->
                new FeatureNotFouundException("There is no such feature."));
        return featureConvector.toDTO(feature);
    }

    @Override
    public List<FeatureDTO> getAll() {
        List<Feature> features = featureRepository.findAll();
        List<FeatureDTO> featureDTOs = new ArrayList<>();
        for (Feature feature : features) {
            featureDTOs.add(featureConvector.toDTO(feature));
        }
        return featureDTOs;
    }

    @Override
    public FeatureDTO create(FeatureDTO featureDTO) {
        Feature feature = featureConvector.fromDTO(featureDTO);
        feature = featureRepository.saveAndFlush(feature);
        return featureConvector.toDTO(feature);
    }

    @Override
    public FeatureDTO update(FeatureDTO featureDTO) {
        Feature feature = featureConvector.fromDTO(featureDTO);
        feature = featureRepository.findById(feature.getId()).orElseThrow(()->
                new FeatureNotFouundException("There is no such feature."));
        feature = featureRepository.saveAndFlush(feature);
        return featureConvector.toDTO(feature);
    }

    @Override
    public void deleteById(int id) {
       Feature feature = featureRepository.findById(id).orElseThrow(()->
                new FeatureNotFouundException("There is no such feature."));
       featureRepository.deleteById(id);
    }
}
