package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.convector.RoomConvector;
import by.nc.tarazenko.dtos.RoomDTO;
import by.nc.tarazenko.entity.Room;
import by.nc.tarazenko.repository.RoomRepositoy;
import by.nc.tarazenko.service.RoomService;
import by.nc.tarazenko.service.exceptions.RoomNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    private Logger logger = Logger.getLogger(RoomService.class);

    @Autowired
    RoomRepositoy roomRepositoy;

    private RoomConvector roomConvector = new RoomConvector();

    @Override
    public RoomDTO getById(int id) {
        Room room = roomRepositoy.findById(id).orElseThrow(()->
                new RoomNotFoundException("There is no such room."));
        return roomConvector.toDTO(room);
    }

    @Override
    public List<RoomDTO> getAll() {
        List<Room> rooms = roomRepositoy.findAll();
        List<RoomDTO> roomDTOs = new ArrayList<>();
        for (Room room : rooms) {
            roomDTOs.add(roomConvector.toDTO(room));
        }
        return roomDTOs;
    }

    @Override
    public RoomDTO create(RoomDTO roomDTO) {
        Room room = roomConvector.fromDTO(roomDTO);
        room = roomRepositoy.saveAndFlush(room);
        return roomConvector.toDTO(room);
    }

    @Override
    public RoomDTO update(RoomDTO roomDTO) {
        Room room = roomConvector.fromDTO(roomDTO);
        room = roomRepositoy.findById(room.getId()).orElseThrow(()->
                new RoomNotFoundException("There is no such room."));
        return roomConvector.toDTO(room);
    }

    @Override
    public void deleteById(int id) {
        Room room = roomRepositoy.findById(id).orElseThrow(()->
                new RoomNotFoundException("There is no such room."));
        roomRepositoy.deleteById(id);
    }
}
