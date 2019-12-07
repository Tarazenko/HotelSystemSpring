package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.convector.FeatureConvector;
import by.nc.tarazenko.dtos.FeatureDTO;
import by.nc.tarazenko.entity.Feature;
import by.nc.tarazenko.entity.Room;
import by.nc.tarazenko.repository.FeatureRepository;
import by.nc.tarazenko.service.FeatureService;
import by.nc.tarazenko.service.RoomService;
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
        Feature feature = featureRepository.findById(id).get();
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
    public void create(FeatureDTO featureDTO) {
        Feature feature = featureConvector.fromDTO(featureDTO);
        featureRepository.saveAndFlush(feature);
    }

    @Override
    public boolean update(FeatureDTO featureDTO) {
        Feature feature = featureConvector.fromDTO(featureDTO);
        boolean ok = true;
        if (featureRepository.findById(feature.getId()).isPresent()) {
            featureRepository.saveAndFlush(feature);
        } else {
            ok = false;
        }
        return ok;
    }

    @Override
    public boolean deleteById(int id) {
        boolean ok = true;
        if (featureRepository.findById(id).isPresent()) {
            featureRepository.deleteById(id);
        } else {
            ok = false;
        }
        return ok;
    }
}
