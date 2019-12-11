package by.nc.tarazenko.service;

import by.nc.tarazenko.dtos.RoomDTO;

import java.time.LocalDate;
import java.util.List;

public interface RoomService extends Service<RoomDTO> {
    List<RoomDTO> getFree(LocalDate checkin, LocalDate checkout);
}
