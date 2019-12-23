package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.convector.FeatureConvector;
import by.nc.tarazenko.dtos.FeatureDTO;
import by.nc.tarazenko.entity.Feature;
import by.nc.tarazenko.repository.FeatureRepository;
import by.nc.tarazenko.service.exceptions.FeatureAlreadyExistException;
import by.nc.tarazenko.service.exceptions.FeatureNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FeatureServiceImplTest {

    @Mock
    private FeatureRepository featureRepository;

    @InjectMocks
    private FeatureServiceImpl featureService;

    private final FeatureConvector featureConvector = new FeatureConvector();

    private Feature feature;

    @Before
    public void init(){
        feature = new Feature();
        feature.setName("Single");
        feature.setCost(123);
        feature.setId(1);
    }

    @Test
    public void getByIdExpectFeatureById() {
        doReturn(Optional.of(feature)).when(featureRepository).findById(anyInt());
        FeatureDTO featureDTOexpect = featureConvector.toDTO(feature);
        FeatureDTO featureDTO = featureService.getById(1);
        assertEquals(featureDTOexpect, featureDTO);
    }

    @Test(expected = FeatureNotFoundException.class)
    public void getByIdWhenRoomNotFound() {
        when(featureRepository.findById(anyInt())).thenReturn(Optional.empty());
        featureService.getById(0);
    }

    @Test
    public void getAllFeaturesAndReturnListOfFeatures() {
        doReturn(Collections.singletonList(feature)).when(featureRepository).findAll();
        List<FeatureDTO> featureDTOsExpected = Collections.singletonList(featureConvector.toDTO(feature));
        List<FeatureDTO> featureDTOs = featureService.getAll();
        verify(featureRepository).findAll();
        assertEquals(featureDTOsExpected, featureDTOs);
    }

    @Test
    public void createFeatureReturnedCreatedFeature() {
        doReturn(feature).when(featureRepository).saveAndFlush(any());
        FeatureDTO featureDTOexpect = featureConvector.toDTO(feature);
        FeatureDTO featureDTO = featureService.create(featureDTOexpect);
        assertEquals(featureDTOexpect, featureDTO);
        verify(featureRepository).saveAndFlush(feature);
    }

    @Test(expected = FeatureAlreadyExistException.class)
    public void createFeatureWhenFeatureAlreadyExistThrowAlreadyExistException() {
        doReturn(feature).when(featureRepository).getFeatureByName(anyString());
        FeatureDTO featureDTOexpect = featureConvector.toDTO(feature);
        featureService.create(featureDTOexpect);
    }

    @Test
    public void updateFeatureReturnedFeatureWhichWasUpdated() {
        doReturn(feature).when(featureRepository).saveAndFlush(any());
        doReturn(Optional.of(feature)).when(featureRepository).findById(anyInt());
        FeatureDTO featureDTOexpect = featureConvector.toDTO(feature);
        FeatureDTO featureDTO = featureService.update(featureDTOexpect);
        assertEquals(featureDTOexpect, featureDTO);
        verify(featureRepository).saveAndFlush(feature);
        verify(featureRepository).findById(anyInt());
    }

    @Test(expected = FeatureNotFoundException.class)
    public void updateWhenRoomNotFoundAndThrowNotFoundException() {
        when(featureRepository.findById(any())).thenReturn(Optional.empty());
        featureService.update(new FeatureDTO());
    }

    @Test
    public void deleteByIdCheckMethodCall() {
        doReturn(Optional.of(feature)).when(featureRepository).findById(anyInt());
        featureService.deleteById(1);
        verify(featureRepository).deleteById(anyInt());
    }
}