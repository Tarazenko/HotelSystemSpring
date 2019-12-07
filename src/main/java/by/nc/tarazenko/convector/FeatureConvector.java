package by.nc.tarazenko.convector;

import by.nc.tarazenko.dtos.FeatureDTO;
import by.nc.tarazenko.entity.Feature;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FeatureConvector implements Convector<FeatureDTO,Feature> {

    @Override
    public FeatureDTO toDTO(Feature feature) {
        FeatureDTO featureDTO = new FeatureDTO();
        featureDTO.setCost(feature.getCost());
        featureDTO.setId(feature.getId());
        featureDTO.setName(feature.getName());
        return featureDTO;
    }

    @Override
    public Feature fromDTO(FeatureDTO featureDTO) {
        Feature feature = new Feature();
        feature.setId(featureDTO.getId());
        feature.setCost(featureDTO.getCost());
        feature.setName(featureDTO.getName());
        return feature;
    }
}
