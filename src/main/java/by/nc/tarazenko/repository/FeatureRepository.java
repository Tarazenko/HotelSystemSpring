package by.nc.tarazenko.repository;

import by.nc.tarazenko.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<Feature, Integer> {
}
