package by.nc.tarazenko.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

//@Data
//@Entity
//@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private int roomId;
    private int roomNumber;
    private List<Feature> features;
}
