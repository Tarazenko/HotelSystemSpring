package by.nc.tarazenko.dtos;

import by.nc.tarazenko.entity.Feature;
import lombok.Data;

import java.util.List;

@Data
public class RoomDTO {
    private int id;
    private int number;
    private List<Feature> features;
    private double cost;
}
